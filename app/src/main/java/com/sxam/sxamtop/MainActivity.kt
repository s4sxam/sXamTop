package com.sxam.sxamtop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sxam.sxamtop.navigation.AppNavigation
import com.sxam.sxamtop.ui.theme.SXamTopTheme
import com.sxam.sxamtop.datastore.SettingsDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsDataStore = SettingsDataStore(this)
            val theme by settingsDataStore.themeFlow.collectAsState(initial = "dark")
            val amoled by settingsDataStore.amoledBlackFlow.collectAsState(initial = true)

            SXamTopTheme(darkTheme = theme != "light", amoledBlack = amoled) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(settingsDataStore = settingsDataStore)
                }
            }
        }
    }
}
