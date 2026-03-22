package com.sxam.sxamtop.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sxam.sxamtop.data.local.AppDatabase
import com.sxam.sxamtop.data.local.UserPostEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PostUiState(
    val isSubmitted: Boolean = false,
    val error: String? = null
)

@HiltViewModel // FIX: Added Hilt ViewModel
class PostNewsViewModel @Inject constructor(
    private val db: AppDatabase // FIX: Safely injected, no longer manually instantiated
) : ViewModel() { // FIX: Converted from AndroidViewModel

    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState

    fun submitPost(
        title: String,
        description: String,
        source: String,
        category: String,
        url: String,
        imageUrl: String? // FIX: Correctly accepts imageUrl now
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
                    imageUrl = imageUrl?.ifBlank { null }, // FIX: Safely inserted into the entity
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