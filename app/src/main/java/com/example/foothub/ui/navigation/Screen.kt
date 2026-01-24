package com.example.foothub.ui.navigation

sealed class Screen(val route: String) {

    object List : Screen("list")
    object Detail : Screen("detail")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
    object About : Screen("about")
}
