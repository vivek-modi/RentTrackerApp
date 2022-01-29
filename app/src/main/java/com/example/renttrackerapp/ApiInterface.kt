package com.example.renttrackerapp

import com.example.renttrackerapp.modal.AddHome
import com.example.renttrackerapp.modal.HomeResults
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @GET("/homes?page=1")
    fun getHomeResults(): Call<HomeResults>

    @POST("/home")
    fun addHome(@Body link: String): Call<Void>

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
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}