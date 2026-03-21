package com.sxam.sxamtop.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {

    companion object {
        val THEME_KEY = stringPreferencesKey("theme")
        val AMOLED_BLACK_KEY = booleanPreferencesKey("amoled_black")
        val NEWS_API_KEY = stringPreferencesKey("news_api_key")
    }

    val themeFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[THEME_KEY] ?: "dark"
    }

    val amoledBlackFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[AMOLED_BLACK_KEY] ?: true
    }

    val newsApiKeyFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[NEWS_API_KEY] ?: ""
    }

    suspend fun setTheme(theme: String) {
        context.dataStore.edit { it[THEME_KEY] = theme }
    }

    suspend fun setAmoledBlack(enabled: Boolean) {
        context.dataStore.edit { it[AMOLED_BLACK_KEY] = enabled }
    }

    suspend fun setNewsApiKey(key: String) {
        context.dataStore.edit { it[NEWS_API_KEY] = key }
    }
}
