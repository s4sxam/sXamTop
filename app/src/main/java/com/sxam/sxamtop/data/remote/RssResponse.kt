package com.sxam.sxamtop.data.remote

import com.google.gson.annotations.SerializedName

data class RssResponse(
    @SerializedName("status") val status: String,
    @SerializedName("items") val items: List<RssItem> = emptyList()
)

data class RssItem(
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("author") val author: String = "",
    @SerializedName("pubDate") val pubDate: String = "",
    @SerializedName("link") val link: String = ""
)
