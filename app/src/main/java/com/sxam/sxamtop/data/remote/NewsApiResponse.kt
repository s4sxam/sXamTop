package com.sxam.sxamtop.data.remote

import com.google.gson.annotations.SerializedName

data class NewsApiResponse(
    @SerializedName("status") val status: String,
    @SerializedName("articles") val articles: List<NewsApiArticle> = emptyList()
)

data class NewsApiArticle(
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("source") val source: NewsApiSource = NewsApiSource(),
    @SerializedName("publishedAt") val publishedAt: String = "",
    @SerializedName("url") val url: String = ""
)

data class NewsApiSource(
    @SerializedName("name") val name: String = ""
)
