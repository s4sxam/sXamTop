package com.sxam.sxamtop

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.sxam.sxamtop.datastore.SettingsDataStore
import com.sxam.sxamtop.navigation.AppNavigation
import com.sxam.sxamtop.ui.theme.SXamTopTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingsDataStore: SettingsDataStore // #10 FIX: Properly injected via Hilt

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _: Boolean -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) // #13 FIX: Request permission
        }

        setContent {
            val theme by settingsDataStore.themeFlow.collectAsState(initial = "dark")
            val amoled by settingsDataStore.amoledBlackFlow.collectAsState(initial = true)

            SXamTopTheme(darkTheme = theme != "light", amoledBlack = amoled) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation() // Dependencies handled by viewmodels now
                }
            }
        }
    }
}