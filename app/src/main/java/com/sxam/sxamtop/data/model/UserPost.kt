package com.sxam.sxamtop.data.model

data class UserPost(
    val id: Int = 0,
    val title: String,
    val description: String,
    val source: String,
    val category: String,
    val url: String,
    val createdAt: Long
)
