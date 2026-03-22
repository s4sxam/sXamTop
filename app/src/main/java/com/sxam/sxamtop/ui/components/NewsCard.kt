package com.sxam.sxamtop.ui.components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sxam.sxamtop.data.model.NewsItem
import com.sxam.sxamtop.ui.theme.TextMeta

@Composable
fun NewsCard(item: NewsItem, onBookmark: () -> Unit, onClick: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth().padding(12.dp).clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarInitials(name = item.source)
                Spacer(modifier = Modifier.width(8.dp))
                Text(item.source, style = MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.weight(1f))
                Text(item.category, style = MaterialTheme.typography.labelSmall, color = TextMeta)
            }
            
            Spacer(modifier = Modifier.height(12.dp))

            if (!item.imageUrl.isNullOrBlank()) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            Text(text = item.title, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.description, style = MaterialTheme.typography.bodyMedium, maxLines = 3, overflow = TextOverflow.Ellipsis)

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = if (item.isUserPost) "User Post" else "Article", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.weight(1f))
                
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "${item.title}\n${item.url}")
                    }
                    context.startActivity(Intent.createChooser(intent, "Share"))
                }) { Icon(Icons.Default.Share, "Share", tint = TextMeta, modifier = Modifier.size(20.dp)) }

                IconButton(onClick = onBookmark) { 
                    Icon(
                        imageVector = if (item.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (item.isBookmarked) MaterialTheme.colorScheme.primary else TextMeta,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}