package com.sxam.sxamtop.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "user_posts")
data class UserPostEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(), // #30 Use String UUID
    val title: String,
    val description: String,
    val source: String,
    val category: String,
    val url: String,
    val imageUrl: String?, 
    val createdAt: Long
)