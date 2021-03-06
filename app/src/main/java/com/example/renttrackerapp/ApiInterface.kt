package com.example.renttrackerapp

import com.example.renttrackerapp.model.HomeResults
import com.example.renttrackerapp.model.RentTrackerMessage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @GET("/homes")
    suspend fun getHomeResults(@Query("page") page: Int): HomeResults

    @POST("/home")
    suspend fun addHome(@Body addHomeRequest: RequestBody): RentTrackerMessage

    @DELETE("/home")
    suspend fun deleteHome(@Query("id") id: Int): RentTrackerMessage

    companion object {
        private var BASE_URL = "https://rent-tracker-rupal.herokuapp.com"

        fun create(): ApiInterface {

            val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val clientSetup = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES) // write timeout
                .readTimeout(1, TimeUnit.MINUTES) // read timeout
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .client(clientSetup)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}