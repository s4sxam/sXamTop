package com.sxam.sxamtop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val avatarColors = listOf(
    Color(0xFF4FC3F7),
    Color(0xFF81C784),
    Color(0xFFFFB74D),
    Color(0xFFE57373),
    Color(0xFFBA68C8),
    Color(0xFF4DD0E1),
    Color(0xFFAED581),
    Color(0xFFFF8A65)
)

@Composable
fun AvatarInitials(
    name: String,
    size: Dp = 36.dp,
    fontSize: Int = 14
) {
    val initial = name.firstOrNull()?.uppercaseChar() ?: '?'
    val colorIndex = (name.hashCode() and 0x7FFFFFFF) % avatarColors.size
    val bgColor = avatarColors[colorIndex]

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(bgColor.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial.toString(),
            color = bgColor,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
