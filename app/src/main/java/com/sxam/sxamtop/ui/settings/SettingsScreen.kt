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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sxam.sxamtop.datastore.SettingsDataStore
import com.sxam.sxamtop.ui.components.SectionHeader
import com.sxam.sxamtop.ui.theme.TealAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsDataStore: SettingsDataStore,
    viewModel: SettingsViewModel = viewModel()
) {
    val context = LocalContext.current
    val theme by viewModel.theme.collectAsState()
    val amoledBlack by viewModel.amoledBlack.collectAsState()
    val savedApiKey by viewModel.newsApiKey.collectAsState()
    var apiKeyInput by remember(savedApiKey) { mutableStateOf(savedApiKey) }

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
                    onClick = { viewModel.clearUserPosts() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Clear User Posts")
                }
                OutlinedButton(
                    onClick = { viewModel.clearBookmarks() },
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
                    trailingContent = { Text("1.0.0", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ListItem(
                    headlineContent = { Text("GitHub") },
                    trailingContent = { Text("s4sxam", color = TealAccent) },
                    modifier = Modifier.then(
                        Modifier.padding(0.dp)
                    )
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
