package com.example.renttrackerapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.renttrackerapp.modal.AddHome
import com.example.renttrackerapp.modal.HomeResults
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.await

class ActivityViewModel : ViewModel() {

    companion object {
        private const val HTTPS_SCHEME = "https"
    }

    var homeResultsLiveData = MutableStateFlow<UiState>(UiState.OnEmpty)

    fun fetchHomes() {
        viewModelScope.launch {
            homeResultsLiveData.value = UiState.IsLoading(true)
            val response = ApiInterface.create().getHomeResults()
            response.enqueue(object : retrofit2.Callback<HomeResults> {
                override fun onResponse(call: Call<HomeResults>, response: Response<HomeResults>) {
                    if (response.isSuccessful) {
                        homeResultsLiveData.value = UiState.IsLoading(false)
                        homeResultsLiveData.value =
                            response.body()?.results?.let { list ->
                                UiState.OnSuccess(result = list)
                            } ?: kotlin.run {
                                UiState.OnEmpty
                            }
                    }
                }

                override fun onFailure(call: Call<HomeResults>, t: Throwable) {
                    homeResultsLiveData.value = UiState.OnError(t)
                }
            })
        }
    }

    fun extractLink(link: String): String {
        val startIndex = link.indexOf(HTTPS_SCHEME)
        val lastIndex = link.length
        return link.substring(startIndex, lastIndex)
    }

    fun addHome(link: String) {
        viewModelScope.launch {
            val response = ApiInterface.create().addHome(AddHome(link))

            response.enqueue(object : retrofit2.Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        fetchHomes()
                        Log.e("isSuccessful", "Done")
                    } else {
                        Log.e("is", "Not Done $response")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    homeResultsLiveData.value = UiState.OnError(t)
                }
            })
        }
    }
}

