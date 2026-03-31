package com.sxam.sxamtop.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(context: Context) {
    // Use applicationContext to prevent Activity memory leaks
    private val appContext = context.applicationContext

    companion object {
        val THEME_KEY = stringPreferencesKey("theme")
        val AMOLED_BLACK_KEY = booleanPreferencesKey("amoled_black")
        val NEWS_API_KEY = stringPreferencesKey("news_api_key")
        
        // Default Key provided by you
        const val DEFAULT_API_KEY = "a1805c41c99c2f186c23411911fefd7e"
    }

    val themeFlow: Flow<String> = appContext.dataStore.data.map { prefs ->
        prefs[THEME_KEY] ?: "dark"
    }

    val amoledBlackFlow: Flow<Boolean> = appContext.dataStore.data.map { prefs ->
        prefs[AMOLED_BLACK_KEY] ?: true
    }

    val newsApiKeyFlow: Flow<String> = appContext.dataStore.data.map { prefs ->
        val savedKey = prefs[NEWS_API_KEY] ?: ""
        // If the user hasn't set a custom key, use your default key
        if (savedKey.isBlank()) DEFAULT_API_KEY else savedKey
    }

    suspend fun setTheme(theme: String) {
        appContext.dataStore.edit { it[THEME_KEY] = theme }
    }

    suspend fun setAmoledBlack(enabled: Boolean) {
        appContext.dataStore.edit { it[AMOLED_BLACK_KEY] = enabled }
    }

    suspend fun setNewsApiKey(key: String) {
        appContext.dataStore.edit { it[NEWS_API_KEY] = key }
    }
}