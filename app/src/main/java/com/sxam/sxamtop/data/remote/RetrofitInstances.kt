package com.sxam.sxamtop.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstances {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        // Set to NONE so your API key isn't leaked in Android Studio Logcat
        level = HttpLoggingInterceptor.Level.NONE
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    val rssApi: RssApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.rss2json.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RssApiService::class.java)
    }

    val newsApi: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://gnews.io/api/v4/") // GNews integration
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
}