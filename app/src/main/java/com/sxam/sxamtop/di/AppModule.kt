package com.sxam.sxamtop.di

import android.content.Context
import com.sxam.sxamtop.BuildConfig
import com.sxam.sxamtop.data.local.AppDatabase
import com.sxam.sxamtop.data.remote.NewsApiService
import com.sxam.sxamtop.data.remote.RssApiService
import com.sxam.sxamtop.datastore.SettingsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore(context) // #11 Singleton Settings
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context) // #32 Safe Context
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE // #41 Fixed Logging
        }
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(client: OkHttpClient): NewsApiService = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApiService::class.java)

    @Provides
    @Singleton
    fun provideRssApi(client: OkHttpClient): RssApiService = Retrofit.Builder()
        .baseUrl("https://api.rss2json.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RssApiService::class.java)
}