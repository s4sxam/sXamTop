package com.sxam.sxamtop.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sxam.sxamtop.data.model.NewsItem
import com.sxam.sxamtop.data.repository.NewsRepository
import com.sxam.sxamtop.datastore.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

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
                allNews.value = (rss + api).distinctBy { it.id }.sortedByDescending { it.publishedAt }
            } catch (e: Exception) { }
        }
    }

    fun toggleBookmark(item: NewsItem) {
        viewModelScope.launch {
            if (item.isBookmarked) repository.removeBookmark(item) else repository.addBookmark(item)
        }
    }
}