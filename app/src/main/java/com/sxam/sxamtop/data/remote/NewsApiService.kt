package com.sxam.sxamtop.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "in",
        @Query("pageSize") pageSize: Int = 50,
        @Query("apiKey") apiKey: String
    ): NewsApiResponse
}
