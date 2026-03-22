package com.sxam.sxamtop.data.local
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsCacheDao {
    @Query("SELECT * FROM news_cache ORDER BY publishedAt DESC")
    fun getAllCachedNews(): Flow<List<NewsCacheEntity>>

    @Query("SELECT * FROM news_cache WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchNews(query: String): Flow<List<NewsCacheEntity>> // #20 Search Description

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsCacheEntity>)

    @Query("SELECT * FROM news_cache WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): NewsCacheEntity? // #1 Fixes Memory Leak
}