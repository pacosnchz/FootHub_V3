package com.example.foothub.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.foothub.model.Player
import com.example.foothub.ui.screens.*
import com.example.foothub.viewmodel.FavoritesViewModel
import com.example.foothub.viewmodel.PlayerViewModel

@Composable
fun AppNavigation(windowSizeClass: WindowSizeClass) {
    val navController    = rememberNavController()
    var selectedPlayer   by remember { mutableStateOf<Player?>(null) }

    // ViewModels compartidos entre pantallas para sincronizar estado
    val playerViewModel: PlayerViewModel       = viewModel()
    val favoritesViewModel: FavoritesViewModel = viewModel()

    // SnackBar para "ya es favorito"
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar        = { BottomBar(navController) },
        snackbarHost     = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        NavHost(
            navController    = navController,
            startDestination = Screen.List.route,
            modifier         = Modifier.padding(paddingValues)
        ) {

            composable(Screen.List.route) {
                PlayerListScreen(
                    onPlayerClick    = { player ->
                        selectedPlayer = player
                        navController.navigate(Screen.Detail.route)
                    },
                    onInfoClick      = { navController.navigate(Screen.About.route) },
                    onShowToast      = { name ->
                        // Coroutine ya gestionada por el SnackbarHostState
                        // (se llama desde el lambda del ViewModel)
                    },
                    playerViewModel  = playerViewModel
                )
            }

            composable(Screen.Detail.route) {
                selectedPlayer?.let { player ->
                    PlayerDetailScreen(
                        player             = player,
                        favoritesViewModel = favoritesViewModel
                    )
                }
            }

            composable(Screen.Favorites.route) {
                PlayerFavoritesScreen(
                    onPlayerClick      = { player ->
                        selectedPlayer = player
                        navController.navigate(Screen.Detail.route)
                    },
                    onInfoClick        = { navController.navigate(Screen.About.route) },
                    favoritesViewModel = favoritesViewModel
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }

            composable(Screen.About.route) {
                AboutScreen()
            }
        }
    }
}