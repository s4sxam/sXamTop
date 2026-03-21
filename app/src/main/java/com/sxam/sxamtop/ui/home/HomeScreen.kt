package com.sxam.sxamtop.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sxam.sxamtop.datastore.SettingsDataStore
import com.sxam.sxamtop.ui.components.*
import com.sxam.sxamtop.ui.theme.TealAccent
import com.sxam.sxamtop.worker.NewsRefreshWorker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    settingsDataStore: SettingsDataStore,
    onNavigateToDetail: (String) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        NewsRefreshWorker.schedule(settingsDataStore.let {
            // We get context via settingsDataStore - use application context
            it.javaClass.getDeclaredField("context").also { f -> f.isAccessible = true }.get(it) as android.content.Context
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "sXamTop",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = TealAccent
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Search, "Search", tint = MaterialTheme.colorScheme.onSurface)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            CategoryChips(
                selected = uiState.selectedCategory,
                onSelect = { viewModel.selectCategory(it) }
            )

            SwipeRefresh(
                state = rememberSwipeRefreshState(uiState.isRefreshing),
                onRefresh = { viewModel.loadNews(isRefresh = true) }
            ) {
                when {
                    uiState.isLoading -> {
                        LazyColumn {
                            items(6) { SkeletonCard() }
                        }
                    }

                    uiState.error != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(uiState.error!!, color = MaterialTheme.colorScheme.error)
                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = { viewModel.loadNews() },
                                    colors = ButtonDefaults.buttonColors(containerColor = TealAccent)
                                ) {
                                    Text("Retry", color = androidx.compose.ui.graphics.Color.Black)
                                }
                            }
                        }
                    }

                    uiState.filteredNews.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No articles found", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    else -> {
                        LazyColumn {
                            items(uiState.filteredNews, key = { it.id }) { item ->
                                NewsCard(
                                    item = item,
                                    onBookmark = { viewModel.toggleBookmark(item) },
                                    onClick = { onNavigateToDetail(item.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
