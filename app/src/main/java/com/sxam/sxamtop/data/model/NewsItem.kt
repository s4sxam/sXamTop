package com.sxam.sxamtop.data.model

data class NewsItem(
    val id: String,
    val title: String,
    val description: String,
    val source: String,
    val url: String,
    val imageUrl: String?, // #6 FIX: Re-added missing property
    val category: String,
    val publishedAt: Long,
    val isUserPost: Boolean = false,
    val isBookmarked: Boolean = false
)