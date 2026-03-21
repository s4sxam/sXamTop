package com.sxam.sxamtop.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val TealAccent = Color(0xFF4FC3F7)
val BackgroundAmoled = Color(0xFF000000)
val BackgroundDark = Color(0xFF0A0A0A)
val SurfaceDark = Color(0xFF111111)
val SurfaceVariantDark = Color(0xFF1A1A1A)
val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFFAAAAAA)
val TextMeta = Color(0xFF888888)
val ChipInactive = Color(0xFF1E1E1E)
val ChipBorder = Color(0xFF333333)
val ErrorRed = Color(0xFFCF6679)

private val DarkColorScheme = darkColorScheme(
    primary = TealAccent,
    onPrimary = Color.Black,
    secondary = TealAccent,
    onSecondary = Color.Black,
    background = BackgroundAmoled,
    surface = SurfaceDark,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    error = ErrorRed,
    outline = ChipBorder,
    surfaceVariant = SurfaceVariantDark
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0077B6),
    onPrimary = Color.White,
    background = Color(0xFFF5F5F5),
    surface = Color(0xFFFFFFFF),
    onBackground = Color(0xFF1A1A1A),
    onSurface = Color(0xFF1A1A1A),
)

@Composable
fun SXamTopTheme(
    darkTheme: Boolean = true,
    amoledBlack: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme.copy(
            background = if (amoledBlack) BackgroundAmoled else BackgroundDark
        )
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SXamTopTypography,
        content = content
    )
}
