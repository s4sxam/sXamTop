package com.sxam.sxamtop.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_posts")
data class UserPostEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val source: String,
    val category: String,
    val url: String,
    val createdAt: Long
)
