package com.sxam.sxamtop.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sxam.sxamtop.data.model.NewsItem
import com.sxam.sxamtop.data.repository.NewsRepository
import com.sxam.sxamtop.datastore.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val news: List<NewsItem> = emptyList(),
    val filteredNews: List<NewsItem> = emptyList(),
    val selectedCategory: String = "All",
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private val bookmarkedIds = MutableStateFlow<Set<String>>(emptySet())

    init {
        observeBookmarks()
        loadNews()
    }

    private fun observeBookmarks() {
        viewModelScope.launch {
            repository.getBookmarks().collect { bookmarks ->
                bookmarkedIds.value = bookmarks.map { it.id }.toSet()
                refreshBookmarkedState()
            }
        }
    }

    private fun refreshBookmarkedState() {
        val ids = bookmarkedIds.value
        val updated = _uiState.value.news.map { it.copy(isBookmarked = ids.contains(it.id)) }
        _uiState.update { state ->
            state.copy(news = updated, filteredNews = filterByCategory(updated, state.selectedCategory))
        }
    }

    fun loadNews(isRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update {
                if (isRefresh) it.copy(isRefreshing = true, error = null)
                else it.copy(isLoading = true, error = null)
            }
            try {
                val apiKey = settingsDataStore.newsApiKeyFlow.first()
                val rssItems = repository.fetchRssNews()
                val newsApiItems = repository.fetchNewsApi(apiKey)
                val userPostItems = repository.getUserPosts().first()

                val allItems = (rssItems + newsApiItems + userPostItems)
                    .distinctBy { it.id }
                    .sortedByDescending { it.publishedAt }
                    .map { it.copy(isBookmarked = bookmarkedIds.value.contains(it.id)) }

                _uiState.update { state ->
                    state.copy(
                        isLoading = false, isRefreshing = false, news = allItems,
                        filteredNews = filterByCategory(allItems, state.selectedCategory), error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, isRefreshing = false, error = "Failed to load news. Tap to retry.")
                }
            }
        }
    }

    fun selectCategory(category: String) {
        _uiState.update { state ->
            state.copy(selectedCategory = category, filteredNews = filterByCategory(state.news, category))
        }
    }

    fun toggleBookmark(item: NewsItem) {
        viewModelScope.launch {
            if (item.isBookmarked) repository.removeBookmark(item) else repository.addBookmark(item)
        }
    }

    private fun filterByCategory(news: List<NewsItem>, category: String): List<NewsItem> = when (category) {
        "All" -> news
        "User Posts" -> news.filter { it.isUserPost }
        else -> news.filter { it.category.equals(category, ignoreCase = true) }
    }
}