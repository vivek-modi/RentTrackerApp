package com.example.renttrackerapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.renttrackerapp.modal.HomeResults
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ActivityViewModel : ViewModel() {

    var homeResultsLiveData = MutableStateFlow<UiState>(UiState.OnEmpty)

    init {
        fetchHomes()
    }

    private fun fetchHomes() {
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
}


