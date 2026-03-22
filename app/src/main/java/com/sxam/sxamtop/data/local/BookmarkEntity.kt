package com.sxam.sxamtop.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val source: String,
    val url: String,
    val imageUrl: String?, // #5 FIX: Image won't be lost on bookmark
    val category: String,
    val publishedAt: Long
)