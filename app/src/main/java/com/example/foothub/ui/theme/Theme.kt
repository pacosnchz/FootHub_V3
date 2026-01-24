package com.example.foothub.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Esquema de colores claro personalizado para FootHub.
 * Basado en tonos azules y adaptado a Material 3.
 */
private val LightColorScheme = lightColorScheme(

    // Colores principales
    primary = FootHubBlue,
    onPrimary = Color.White,
    primaryContainer = SoftBlue,
    onPrimaryContainer = TextPrimary,

    // Colores secundarios
    secondary = FootHubBlueLight,
    onSecondary = TextPrimary,
    secondaryContainer = SoftBlue,
    onSecondaryContainer = TextPrimary,

    // Colores terciarios (acentos)
    tertiary = FavoriteBlue,
    onTertiary = Color.White,

    // Fondo y superficies
    background = WhiteBackground,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,

    // Estados de error
    error = Color(0xFFBA1A1A),
    onError = Color.White
)

/**
 * Tema principal de la aplicación FootHub.
 */
@Composable
fun FootHubTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = FootHubTypography,
        content = content
    )
}
