package com.gustavofleck.data.api

import com.gustavofleck.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventsServiceProvider {

    private val httpInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val httpClient = OkHttpClient.Builder().addInterceptor(httpInterceptor).build()

    fun serviceInstance(): EventsService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(EventsService::class.java)
    }
}