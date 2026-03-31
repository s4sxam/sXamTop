package com.sxam.sxamtop.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("category") category: String = "general",
        @Query("lang") lang: String = "en",
        @Query("max") max: Int = 50,
        @Query("apikey") apikey: String // GNews uses lowercase 'apikey'
    ): NewsApiResponse
}