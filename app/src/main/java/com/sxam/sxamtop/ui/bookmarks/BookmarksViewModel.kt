package com.sxam.sxamtop.ui.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sxam.sxamtop.data.model.NewsItem
import com.sxam.sxamtop.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val bookmarks: StateFlow<List<NewsItem>> = repository.getBookmarks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeBookmark(item: NewsItem) {
        viewModelScope.launch {
            repository.removeBookmark(item)
        }
    }
}