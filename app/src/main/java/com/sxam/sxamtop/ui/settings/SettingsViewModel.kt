package com.sxam.sxamtop.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sxam.sxamtop.data.local.AppDatabase
import com.sxam.sxamtop.data.repository.NewsRepository
import com.sxam.sxamtop.datastore.SettingsDataStore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    val settingsDataStore = SettingsDataStore(application)
    private val repository = NewsRepository(AppDatabase.getInstance(application))

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
