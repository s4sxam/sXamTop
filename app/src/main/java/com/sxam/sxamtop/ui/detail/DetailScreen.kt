package com.sxam.sxamtop.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sxam.sxamtop.ui.components.AvatarInitials
import com.sxam.sxamtop.ui.components.timeAgo
import com.sxam.sxamtop.ui.theme.*

// Shared state: simple in-memory store for news items navigation
object NewsDetailStore {
    val items = mutableMapOf<String, com.sxam.sxamtop.data.model.NewsItem>()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    newsId: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val item = NewsDetailStore.items[newsId]

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    item?.let { news ->
                        IconButton(onClick = {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, "${news.title}\n${news.url}")
                            }
                            context.startActivity(Intent.createChooser(intent, "Share"))
                        }) {
                            Icon(Icons.Default.Share, "Share")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        if (item == null) {
            Box(Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Article not found", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AvatarInitials(name = item.source, size = 40.dp, fontSize = 16)
                    Column {
                        Text(item.source, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                        Text(timeAgo(item.publishedAt), style = MaterialTheme.typography.bodySmall, color = TextMeta)
                    }
                    Spacer(Modifier.weight(1f))
                    Surface(
                        color = TealAccent.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = item.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = TealAccent,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary
                )

                if (item.description.isNotBlank()) {
                    Spacer(Modifier.height(14.dp))
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        lineHeight = androidx.compose.ui.unit.TextUnit(22f, androidx.compose.ui.unit.TextUnitType.Sp)
                    )
                }

                Spacer(Modifier.height(28.dp))

                if (item.url.isNotBlank()) {
                    Button(
                        onClick = {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.url)))
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TealAccent),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Open Original Article", color = Color.Black)
                    }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}
