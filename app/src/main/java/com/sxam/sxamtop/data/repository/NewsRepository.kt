package com.sxam.sxamtop.data.repository

import com.sxam.sxamtop.data.local.AppDatabase
import com.sxam.sxamtop.data.local.BookmarkEntity
import com.sxam.sxamtop.data.model.NewsItem
import com.sxam.sxamtop.data.remote.RetrofitInstances
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

class NewsRepository(private val db: AppDatabase) {

    private val rssApi = RetrofitInstances.rssApi
    private val newsApi = RetrofitInstances.newsApi

    // In-memory cache so DetailViewModel can look up articles by ID
    private val newsCache = mutableMapOf<String, NewsItem>()

    suspend fun getNewsById(id: String): NewsItem? = newsCache[id]

    fun getBookmarks(): Flow<List<NewsItem>> {
        return db.bookmarkDao().getAllBookmarks().map { entities ->
            entities.map { e ->
                NewsItem(
                    id = e.id, title = e.title, description = e.description,
                    source = e.source, url = e.url, imageUrl = e.imageUrl,
                    category = e.category, publishedAt = e.publishedAt, isBookmarked = true
                )
            }
        }
    }

    fun getUserPosts(): Flow<List<NewsItem>> {
        return db.userPostDao().getAllUserPosts().map { entities ->
            entities.map { e ->
                NewsItem(
                    id = "user_${e.id}", title = e.title, description = e.description,
                    source = e.source, url = e.url, imageUrl = null,
                    category = e.category, publishedAt = e.createdAt, isUserPost = true
                )
            }
        }
    }

    suspend fun fetchRssNews(): List<NewsItem> {
        return try {
            val response = rssApi.getGoogleNews()
            response.items.map { item ->
                NewsItem(
                    id = md5(item.title),
                    title = item.title.trim(),
                    description = stripHtml(item.description),
                    source = if (item.author.isNotBlank()) item.author else "Google News",
                    url = item.link,
                    imageUrl = item.thumbnail ?: item.enclosure?.link,
                    category = "Top",
                    publishedAt = parseRssDate(item.pubDate)
                )
            }.also { items -> newsCache.putAll(items.associateBy { it.id }) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun fetchNewsApi(apiKey: String): List<NewsItem> {
        if (apiKey.isBlank()) return emptyList()
        return try {
            val response = newsApi.getTopHeadlines(apikey = apiKey)
            response.articles.map { article ->
                NewsItem(
                    id = md5(article.title),
                    title = article.title.trim(),
                    description = article.description?.let { stripHtml(it) } ?: "",
                    source = article.source.name,
                    url = article.url,
                    imageUrl = null,
                    category = "Top",
                    publishedAt = parseIsoDate(article.publishedAt)
                )
            }.also { items -> newsCache.putAll(items.associateBy { it.id }) }
        } catch (e: HttpException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addBookmark(item: NewsItem) {
        db.bookmarkDao().insertBookmark(
            BookmarkEntity(item.id, item.title, item.description, item.source, item.url, item.imageUrl, item.category, item.publishedAt)
        )
    }

    suspend fun removeBookmark(item: NewsItem) {
        db.bookmarkDao().deleteBookmark(
            BookmarkEntity(item.id, item.title, item.description, item.source, item.url, item.imageUrl, item.category, item.publishedAt)
        )
    }

    suspend fun clearBookmarks() = db.bookmarkDao().clearAll()
    suspend fun clearUserPosts() = db.userPostDao().clearAll()

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return md.digest(input.toByteArray()).joinToString("") { "%02x".format(it) }
    }

    private fun stripHtml(html: String): String {
        return html.replace(Regex("<[^>]*>"), "").trim()
    }

    private fun parseRssDate(date: String): Long {
        return try {
            val sdf = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
            sdf.parse(date)?.time ?: System.currentTimeMillis()
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    }

    private fun parseIsoDate(date: String): Long {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            sdf.parse(date)?.time ?: System.currentTimeMillis()
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    }
}