package com.sxam.sxamtop.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sxam.sxamtop.ui.theme.TextMeta

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = TextMeta,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}
