package com.sxam.sxamtop.ui.detail
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sxam.sxamtop.data.model.NewsItem
import com.sxam.sxamtop.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: NewsRepository,
    savedStateHandle: SavedStateHandle // #1 Get ID safely
) : ViewModel() {
    private val newsId: String = savedStateHandle.get<String>("newsId") ?: ""
    private val _newsItem = MutableStateFlow<NewsItem?>(null)
    val newsItem = _newsItem.asStateFlow()

    init {
        viewModelScope.launch { _newsItem.value = repository.getNewsById(newsId) }
    }
}