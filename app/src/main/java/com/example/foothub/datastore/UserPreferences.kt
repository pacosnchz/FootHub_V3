package com.example.foothub.datastore

/**
 * Opciones de tema de la aplicación.
 * SYSTEM sigue la configuración del dispositivo.
 */
enum class AppTheme {
    LIGHT, DARK, SYSTEM
}

/**
 * Preferencias del usuario guardadas en DataStore.
 */
data class UserPreferences(
    val username: String = "",
    val theme: AppTheme = AppTheme.SYSTEM
)