package com.sxam.sxamtop.data.repository

import com.sxam.sxamtop.data.local.*
import com.sxam.sxamtop.data.model.NewsItem
import com.sxam.sxamtop.data.remote.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val db: AppDatabase,
    private val newsApi: NewsApiService,
    private val rssApi: RssApiService
) {
    fun getCachedNews(): Flow<List<NewsItem>> = db.newsCacheDao().getAllCachedNews().map { list -> list.map { it.toDomainModel() } }
    
    fun searchCachedNews(query: String): Flow<List<NewsItem>> = db.newsCacheDao().searchNews(query).map { list -> list.map { it.toDomainModel() } }

    suspend fun getNewsById(id: String): NewsItem? {
        return db.newsCacheDao().getById(id)?.toDomainModel() 
            ?: db.userPostDao().getById(id)?.toDomainModel()
            ?: db.bookmarkDao().getById(id)?.toDomainModel()
    }

    suspend fun refreshNews(apiKey: String): Result<Unit> {
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

    fun getBookmarks(): Flow<List<NewsItem>> = db.bookmarkDao().getAllBookmarks().map { list -> list.map { it.toDomainModel() } }
    
    suspend fun addBookmark(item: NewsItem) { db.bookmarkDao().insertBookmark(item.toBookmarkEntity()) }
    
    suspend fun removeBookmark(item: NewsItem) { db.bookmarkDao().deleteBookmark(item.toBookmarkEntity()) }
    
    suspend fun clearBookmarks() { db.bookmarkDao().clearAll() }

    fun getUserPosts(): Flow<List<NewsItem>> = db.userPostDao().getAllUserPosts().map { list -> list.map { it.toDomainModel() } }
    
    suspend fun clearUserPosts() { db.userPostDao().clearAll() }
}

// Map extensions
fun NewsCacheEntity.toDomainModel() = NewsItem(id, title, description, source, url, imageUrl, category, publishedAt, false, false)
fun BookmarkEntity.toDomainModel() = NewsItem(id, title, description, source, url, imageUrl, category, publishedAt, false, true)
fun UserPostEntity.toDomainModel() = NewsItem(id, title, description, source, url, imageUrl, category, createdAt, true, false)

fun NewsItem.toBookmarkEntity() = BookmarkEntity(id, title, description, source, url, imageUrl, category, publishedAt)

fun RssItem.toNewsCacheEntity(): NewsCacheEntity {
    return NewsCacheEntity(
        id = link,
        title = title,
        description = description,
        source = author.ifBlank { "Google News" },
        url = link,
        imageUrl = thumbnail?.ifBlank { enclosure?.link },
        category = "Top",
        publishedAt = System.currentTimeMillis()
    )
}

fun NewsApiArticle.toNewsCacheEntity(): NewsCacheEntity {
    return NewsCacheEntity(
        id = url ?: "",
        title = title ?: "",
        description = description ?: "",
        source = source?.name ?: "NewsAPI",
        url = url ?: "",
        imageUrl = urlToImage,
        category = "World",
        publishedAt = System.currentTimeMillis()
    )
}