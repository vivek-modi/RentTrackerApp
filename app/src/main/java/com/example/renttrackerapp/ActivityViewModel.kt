package com.example.renttrackerapp

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.renttrackerapp.model.Home
import com.example.renttrackerapp.model.RentTrackerMessage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException

class ActivityViewModel(private val app: Application) : AndroidViewModel(app) {

    companion object {
        private const val HTTPS_SCHEME = "https"
        private const val FORM_DATA_NAME = "link"
    }

    var rentTrackerAdapter: RentTrackerAdapter? = null

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
                rentTrackerAdapter?.refresh()
            } catch (exception: HttpException) {
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val jsonAdapter = moshi.adapter(RentTrackerMessage::class.java)
                exception.response()?.errorBody()?.string()?.let {
                    val errorMessage = jsonAdapter.fromJson(it)
                    Toast.makeText(
                        app.applicationContext,
                        "Error while adding home, details: ${errorMessage?.msg}",
                        Toast.LENGTH_LONG
                    ).show()
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

    fun deleteHome(id: Int) {
        viewModelScope.launch {
            try {
                ApiInterface.create()
                    .deleteHome(id)
                rentTrackerAdapter?.refresh()
            } catch (exception: HttpException) {
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val jsonAdapter = moshi.adapter(RentTrackerMessage::class.java)
                exception.response()?.errorBody()?.string()?.let {
                    val errorMessage = jsonAdapter.fromJson(it)
                    Toast.makeText(
                        app.applicationContext,
                        "Error while deleting home, details: ${errorMessage?.msg}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}


