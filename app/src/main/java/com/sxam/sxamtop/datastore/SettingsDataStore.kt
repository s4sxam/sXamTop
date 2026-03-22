package com.sxam.sxamtop.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {

    companion object {
        val THEME_KEY = stringPreferencesKey("theme")
        val AMOLED_BLACK_KEY = booleanPreferencesKey("amoled_black")
    }

    // Encrypted Shared Preferences for API Keys
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val securePrefs = EncryptedSharedPreferences.create(
        context,
        "secure_settings",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val _newsApiKeyFlow = MutableStateFlow(securePrefs.getString("news_api_key", "") ?: "")

    val themeFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[THEME_KEY] ?: "dark"
    }

    val amoledBlackFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[AMOLED_BLACK_KEY] ?: true
    }

    val newsApiKeyFlow: Flow<String> = _newsApiKeyFlow

    suspend fun setTheme(theme: String) {
        context.dataStore.edit { it[THEME_KEY] = theme }
    }

    suspend fun setAmoledBlack(enabled: Boolean) {
        context.dataStore.edit { it[AMOLED_BLACK_KEY] = enabled }
    }

    suspend fun setNewsApiKey(key: String) {
        securePrefs.edit().putString("news_api_key", key).apply()
        _newsApiKeyFlow.value = key
    }
}