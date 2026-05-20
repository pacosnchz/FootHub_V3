package com.example.foothub.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.foothub.datastore.AppTheme

private val LightColorScheme = lightColorScheme(
    primary             = FootHubBlue,
    onPrimary           = Color.White,
    primaryContainer    = SoftBlue,
    onPrimaryContainer  = TextPrimary,
    secondary           = FootHubBlueLight,
    onSecondary         = TextPrimary,
    secondaryContainer  = SoftBlue,
    onSecondaryContainer= TextPrimary,
    tertiary            = FavoriteBlue,
    onTertiary          = Color.White,
    background          = WhiteBackground,
    onBackground        = TextPrimary,
    surface             = SurfaceWhite,
    onSurface           = TextPrimary,
    error               = Color(0xFFBA1A1A),
    onError             = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary             = FootHubBlueLight,
    onPrimary           = Color(0xFF003060),
    primaryContainer    = FootHubBlue,
    onPrimaryContainer  = Color.White,
    secondary           = SoftBlue,
    onSecondary         = Color(0xFF1C1C1C),
    background          = Color(0xFF121212),
    onBackground        = Color(0xFFE0E0E0),
    surface             = Color(0xFF1E1E1E),
    onSurface           = Color(0xFFE0E0E0),
    error               = Color(0xFFCF6679),
    onError             = Color.Black
)

@Composable
fun FootHubTheme(
    appTheme: AppTheme = AppTheme.SYSTEM,
    content: @Composable () -> Unit
) {
    val useDark = when (appTheme) {
        AppTheme.LIGHT  -> false
        AppTheme.DARK   -> true
        AppTheme.SYSTEM -> isSystemInDarkTheme()
    }

    MaterialTheme(
        colorScheme = if (useDark) DarkColorScheme else LightColorScheme,
        typography  = FootHubTypography,
        content     = content
    )
}