package com.sxam.sxamtop.ui.post

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sxam.sxamtop.data.local.AppDatabase
import com.sxam.sxamtop.data.local.UserPostEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class PostUiState(
    val isSubmitted: Boolean = false,
    val error: String? = null
)

class PostNewsViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState

    fun submitPost(
        title: String,
        description: String,
        source: String,
        category: String,
        url: String
    ) {
        if (title.isBlank() || description.isBlank() || source.isBlank()) {
            _uiState.value = PostUiState(error = "Title, description, and source are required.")
            return
        }

        viewModelScope.launch {
            db.userPostDao().insertUserPost(
                UserPostEntity(
                    title = title.trim(),
                    description = description.trim(),
                    source = source.trim(),
                    category = category,
                    url = url.trim(),
                    createdAt = System.currentTimeMillis()
                )
            )
            _uiState.value = PostUiState(isSubmitted = true)
        }
    }

    fun resetState() {
        _uiState.value = PostUiState()
    }
}
