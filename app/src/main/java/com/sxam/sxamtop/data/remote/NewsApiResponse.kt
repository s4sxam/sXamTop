package com.sxam.sxamtop.data.remote

import com.google.gson.annotations.SerializedName

data class NewsApiResponse(
    @SerializedName("status") 
    val status: String,
    
    @SerializedName("totalResults") 
    val totalResults: Int,
    
    @SerializedName("articles") 
    val articles: List<NewsApiArticle> = emptyList()
)

data class NewsApiArticle(
    @SerializedName("title") 
    val title: String? = "",
    
    @SerializedName("description") 
    val description: String? = "",
    
    @SerializedName("source") 
    val source: NewsApiSource? = null,
    
    @SerializedName("publishedAt") 
    val publishedAt: String? = "",
    
    @SerializedName("url") 
    val url: String? = "",
    
    @SerializedName("urlToImage") 
    val urlToImage: String? = null
)

data class NewsApiSource(
    @SerializedName("id") 
    val id: String? = null,
    
    @SerializedName("name") 
    val name: String? = ""
)