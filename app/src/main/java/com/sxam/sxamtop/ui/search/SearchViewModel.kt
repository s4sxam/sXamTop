package com.sxam.sxamtop.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sxam.sxamtop.data.local.AppDatabase
import com.sxam.sxamtop.data.model.NewsItem
import com.sxam.sxamtop.data.repository.NewsRepository
import com.sxam.sxamtop.datastore.SettingsDataStore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val repository = NewsRepository(db)
    private val settingsDataStore = SettingsDataStore(application)

    private val allNews = MutableStateFlow<List<NewsItem>>(emptyList())
    val query = MutableStateFlow("")

    val results: StateFlow<List<NewsItem>> = query
        .debounce(300)
        .combine(allNews) { q, news ->
            if (q.isBlank()) emptyList()
            else news.filter {
                it.title.contains(q, ignoreCase = true) ||
                it.source.contains(q, ignoreCase = true)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            try {
                val apiKey = settingsDataStore.newsApiKeyFlow.first()
                val rss = repository.fetchRssNews()
                val api = repository.fetchNewsApi(apiKey)
                val combined = (rss + api).distinctBy { it.id }.sortedByDescending { it.publishedAt }
                allNews.value = combined
            } catch (e: Exception) {
                // Ignore safe errors on background search load
            }
        }
    }

    fun toggleBookmark(item: NewsItem) {
        viewModelScope.launch {
            if (item.isBookmarked) repository.removeBookmark(item)
            else repository.addBookmark(item)
        }
    }
}