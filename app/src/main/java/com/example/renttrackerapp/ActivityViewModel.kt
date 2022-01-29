package com.example.renttrackerapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.renttrackerapp.modal.Home
import com.example.renttrackerapp.modal.RentTrackerMessage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException

class ActivityViewModel : ViewModel() {

    companion object {
        private const val HTTPS_SCHEME = "https"
        private const val FORM_DATA_NAME = "link"
    }
     val rentTrackerAdapter = RentTrackerAdapter()

    fun getHomeRequestFlow(): Flow<PagingData<Home>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                RentTrackerPagingSource(
                    RentTrackerDataSource()
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun extractLink(link: String): String {
        val startIndex = link.indexOf(HTTPS_SCHEME)
        val lastIndex = link.length
        return link.substring(startIndex, lastIndex)
    }

    fun addHome(link: String) {
        viewModelScope.launch {
            try {
                ApiInterface.create()
                    .addHome(
                        getRequestBodyForAddHome(link)
                    )
                rentTrackerAdapter.refresh()
            } catch (exception: HttpException) {
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val jsonAdapter = moshi.adapter(RentTrackerMessage::class.java)
                exception.response()?.errorBody()?.string()?.let {
                    val errorMessage = jsonAdapter.fromJson(it)
                    Log.e("exception", "${errorMessage?.msg}")
                }
            }
        }
    }

    private fun getRequestBodyForAddHome(link: String): MultipartBody {
        return MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(FORM_DATA_NAME, link)
            .build()
    }
}


