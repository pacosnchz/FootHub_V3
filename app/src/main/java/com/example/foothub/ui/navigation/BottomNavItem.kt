package com.example.foothub.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {

    object Home : BottomNavItem(
        route = Screen.List.route,
        icon = Icons.Filled.Home,
        label = "Inicio"
    )

    object Favorites : BottomNavItem(
        route = Screen.Favorites.route,
        icon = Icons.Filled.Favorite,
        label = "Favoritos"
    )

    object Profile : BottomNavItem(
        route = Screen.Profile.route,
        icon = Icons.Filled.Person,
        label = "Perfil"
    )
}
