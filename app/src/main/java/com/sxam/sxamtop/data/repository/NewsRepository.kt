package com.sxam.sxamtop.data.repository

import com.sxam.sxamtop.data.local.*
import com.sxam.sxamtop.data.model.NewsItem
import com.sxam.sxamtop.data.remote.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor( // #8 DI
    private val db: AppDatabase,
    private val newsApi: NewsApiService,
    private val rssApi: RssApiService
) {
    // #9 Serve from DB first
    fun getCachedNews(): Flow<List<NewsItem>> = db.newsCacheDao().getAllCachedNews().map { it.map { e -> e.toDomainModel() } }
    fun searchCachedNews(query: String): Flow<List<NewsItem>> = db.newsCacheDao().searchNews(query).map { it.map { e -> e.toDomainModel() } }

    suspend fun getNewsById(id: String): NewsItem? { // #1 Fix detail memory leak
        return db.newsCacheDao().getById(id)?.toDomainModel() 
            ?: db.userPostDao().getById(id)?.toDomainModel()
            ?: db.bookmarkDao().getById(id)?.toDomainModel()
    }

    suspend fun refreshNews(apiKey: String): Result<Unit> { // #12 Better Error Handling
        return try {
            val rssResponse = rssApi.getGoogleNews().items.map { it.toNewsCacheEntity() }
            val apiResponse = if (apiKey.isNotBlank()) {
                newsApi.getTopHeadlines(apiKey = apiKey).articles.map { it.toNewsCacheEntity() }
            } else emptyList()

            db.newsCacheDao().insertAll(rssResponse + apiResponse)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.localizedMessage}"))
        }
    }
    // ... insert/delete bookmark methods remain, mapped to new DB models
}

// Mapper extensions to keep it clean
fun NewsCacheEntity.toDomainModel() = NewsItem(id, title, description, source, url, imageUrl, category, publishedAt)
// Add mappings for APIs