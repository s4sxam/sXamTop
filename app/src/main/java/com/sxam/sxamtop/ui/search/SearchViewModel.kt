package com.sxam.sxamtop.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sxam.sxamtop.data.model.NewsItem
import com.sxam.sxamtop.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    
    val query = MutableStateFlow("")

    val results = query
        .debounce(300)
        .flatMapLatest { q -> 
            if (q.isBlank()) flowOf(emptyList()) else repository.searchCachedNews(q)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleBookmark(item: NewsItem) {
        viewModelScope.launch {
            if (item.isBookmarked) {
                repository.removeBookmark(item)
            } else {
                repository.addBookmark(item)
            }
        }
    }
}