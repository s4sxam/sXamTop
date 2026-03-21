package com.sxam.sxamtop.ui.bookmarks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sxam.sxamtop.data.local.AppDatabase
import com.sxam.sxamtop.data.model.NewsItem
import com.sxam.sxamtop.data.repository.NewsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookmarksViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val repository = NewsRepository(db)

    val bookmarks: StateFlow<List<NewsItem>> = repository.getBookmarks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeBookmark(item: NewsItem) {
        viewModelScope.launch {
            repository.removeBookmark(item)
        }
    }
}
