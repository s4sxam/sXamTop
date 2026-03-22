package com.sxam.sxamtop.ui.components
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
// import coil.compose.AsyncImage // Uncomment after adding to build.gradle (#13)

@Composable
fun NewsCard(item: NewsItem, onBookmark: () -> Unit, onClick: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth().padding(12.dp).clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant) // #23 Fixed Color
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // ... Source row

            // #13 Add Image if exists
            /*
            if (!item.imageUrl.isNullOrBlank()) {
                AsyncImage(model = item.imageUrl, contentDescription = null, modifier = Modifier.fillMaxWidth().height(180.dp))
            }
            */
            
            // ... Title and description

            Row {
                // ... Meta details
                Spacer(modifier = Modifier.weight(1f))
                
                // #17 Add Share button on Card
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "${item.title}\n${item.url}")
                    }
                    context.startActivity(Intent.createChooser(intent, "Share"))
                }) { Icon(Icons.Default.Share, "Share", tint = TextMeta, modifier = Modifier.size(18.dp)) }

                IconButton(onClick = onBookmark) { /* Bookmark logic */ }
            }
        }
    }
}