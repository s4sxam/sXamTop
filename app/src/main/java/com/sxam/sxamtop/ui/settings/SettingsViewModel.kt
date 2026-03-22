package com.sxam.sxamtop.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sxam.sxamtop.data.repository.NewsRepository
import com.sxam.sxamtop.datastore.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel // 1. FIX: Added Hilt Annotation
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore, // 2. FIX: Safely injected, no longer manually instantiated
    private val repository: NewsRepository
) : ViewModel() { // 3. FIX: Changed from AndroidViewModel to standard ViewModel

    val theme: StateFlow<String> = settingsDataStore.themeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "dark")

    val amoledBlack: StateFlow<Boolean> = settingsDataStore.amoledBlackFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val newsApiKey: StateFlow<String> = settingsDataStore.newsApiKeyFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    fun setTheme(theme: String) = viewModelScope.launch { settingsDataStore.setTheme(theme) }
    fun setAmoledBlack(v: Boolean) = viewModelScope.launch { settingsDataStore.setAmoledBlack(v) }
    fun setNewsApiKey(key: String) = viewModelScope.launch { settingsDataStore.setNewsApiKey(key) }

    fun clearBookmarks() = viewModelScope.launch { repository.clearBookmarks() }
    fun clearUserPosts() = viewModelScope.launch { repository.clearUserPosts() }
}