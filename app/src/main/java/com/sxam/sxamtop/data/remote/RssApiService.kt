package com.sxam.sxamtop.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface RssApiService {
    @GET("v1/api.json")
    suspend fun getGoogleNews(
        @Query("rss_url") rssUrl: String = "https://news.google.com/rss",
        @Query("count") count: Int = 50
    ): RssResponse
}
