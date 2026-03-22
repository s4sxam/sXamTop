package com.sxam.sxamtop.data.local
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// #9 Offline Caching, #31 Indexing
@Entity(tableName = "news_cache", indices =[Index("publishedAt")])
data class NewsCacheEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val source: String,
    val url: String,
    val imageUrl: String?, // #13/#29 Image Support
    val category: String,
    val publishedAt: Long
)