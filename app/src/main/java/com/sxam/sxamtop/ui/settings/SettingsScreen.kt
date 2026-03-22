package com.sxam.sxamtop.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sxam.sxamtop.ui.components.SectionHeader
import com.sxam.sxamtop.ui.theme.TealAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel() // 1. FIX: Uses Hilt ViewModel (settingsDataStore parameter safely removed)
) {
    val context = LocalContext.current
    val theme by viewModel.theme.collectAsState()
    val amoledBlack by viewModel.amoledBlack.collectAsState()
    val savedApiKey by viewModel.newsApiKey.collectAsState()
    var apiKeyInput by remember(savedApiKey) { mutableStateOf(savedApiKey) }

    // 2. FIX: Added Dialog states
    var showClearBookmarksDialog by remember { mutableStateOf(false) }
    var showClearPostsDialog by remember { mutableStateOf(false) }

    // 3. FIX: Confirmation Dialog logic added to prevent accidental data wipe
    if (showClearBookmarksDialog) {
        AlertDialog(
            onDismissRequest = { showClearBookmarksDialog = false },
            title = { Text("Clear Bookmarks") },
            text = { Text("Are you sure you want to delete all your saved bookmarks? This cannot be undone.") },
            confirmButton = {
                TextButton(onClick = { 
                    viewModel.clearBookmarks() 
                    showClearBookmarksDialog = false 
                }) {
                    Text("Clear", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearBookmarksDialog = false }) { Text("Cancel") }
            }
        )
    }

    if (showClearPostsDialog) {
        AlertDialog(
            onDismissRequest = { showClearPostsDialog = false },
            title = { Text("Clear User Posts") },
            text = { Text("Are you sure you want to delete all your created posts? This cannot be undone.") },
            confirmButton = {
                TextButton(onClick = { 
                    viewModel.clearUserPosts() 
                    showClearPostsDialog = false 
                }) {
                    Text("Clear", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearPostsDialog = false }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            // SOURCES
            SectionHeader("Sources")
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = apiKeyInput,
                    onValueChange = { apiKeyInput = it },
                    label = { Text("NewsAPI Key") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Button(
                    onClick = { viewModel.setNewsApiKey(apiKeyInput) },
                    colors = ButtonDefaults.buttonColors(containerColor = TealAccent)
                ) {
                    Text("Save", color = Color.Black)
                }
            }

            Spacer(Modifier.height(8.dp))

            // APPEARANCE
            SectionHeader("Appearance")
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Theme", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("dark", "light", "system").forEach { t ->
                        FilterChip(
                            selected = theme == t,
                            onClick = { viewModel.setTheme(t) },
                            label = { Text(t.replaceFirstChar { it.uppercase() }) }
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("AMOLED Black", color = MaterialTheme.colorScheme.onSurface)
                    Switch(
                        checked = amoledBlack,
                        onCheckedChange = { viewModel.setAmoledBlack(it) },
                        colors = SwitchDefaults.colors(checkedTrackColor = TealAccent)
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // DATA
            SectionHeader("Data")
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { showClearPostsDialog = true }, // Opens Dialog
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Clear User Posts")
                }
                OutlinedButton(
                    onClick = { showClearBookmarksDialog = true }, // Opens Dialog
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Clear Bookmarks")
                }
            }

            Spacer(Modifier.height(8.dp))

            // ABOUT
            SectionHeader("About")
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                ListItem(
                    headlineContent = { Text("Version") },
                    trailingContent = { Text("1.0.0", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ListItem(
                    headlineContent = { Text("GitHub") },
                    trailingContent = { Text("s4sxam", color = TealAccent) },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    modifier = Modifier.padding(0.dp)
                )
                Button(
                    onClick = {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/s4sxam"))
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TealAccent),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Open GitHub", color = Color.Black)
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}