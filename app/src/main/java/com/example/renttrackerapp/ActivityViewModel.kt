package com.example.renttrackerapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.renttrackerapp.modal.HomeResults
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class ActivityViewModel : ViewModel() {

    companion object {
        private const val HTTPS_SCHEME = "https"
        private const val FORM_DATA_NAME = "link"
    }

    var homeResultsLiveData = MutableStateFlow<UiState>(UiState.OnEmpty)

    fun fetchHomes() {
        viewModelScope.launch {
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
                    homeResultsLiveData.value = UiState.IsLoading(false)
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

            val response = ApiInterface.create()
                .addHome(
                    getRequestBodyForAddHome(link)
                )

            response.enqueue(object : retrofit2.Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        fetchHomes()
                    } else {
                        homeResultsLiveData.value = UiState.OnError(
                            Throwable(
                                response.message(),
                                Throwable("Http Request Error")
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    homeResultsLiveData.value = UiState.OnError(t)
                }
            })
        }
    }

    private fun getRequestBodyForAddHome(link: String): MultipartBody {
        return MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(FORM_DATA_NAME, link)
            .build()
    }
}


