package com.example.foothub.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.foothub.model.Player
import com.example.foothub.repository.PlayerRepository
import com.example.foothub.ui.screens.*

@Composable
fun AppNavigation(
    windowSizeClass: WindowSizeClass
) {

    val navController = rememberNavController()

    val isExpanded =
        windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact

    var playerList by remember { mutableStateOf(PlayerRepository.players) }
    var selectedPlayer by remember { mutableStateOf<Player?>(null) }
    var isLoggedIn by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Screen.List.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            /* ───────────── HOME / LISTA ───────────── */

            composable(Screen.List.route) {

                if (isExpanded) {
                    // TABLET / EXPANDED
                    Row(modifier = Modifier.fillMaxSize()) {

                        PlayerListScreen(
                            navController = navController,
                            modifier = Modifier.weight(1f)
                        )

                        if (selectedPlayer != null) {
                            PlayerDetailScreen(
                                navController = navController,
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Selecciona un jugador")
                            }
                        }
                    }

                } else {
                    // MÓVIL / COMPACT
                    PlayerListScreen(
                        navController = navController
                    )
                }
            }

            /* ───────────── DETALLE ───────────── */

            composable(Screen.Detail.route) {
                PlayerDetailScreen(navController = navController)
            }

            /* ───────────── FAVORITOS ───────────── */

            composable(Screen.Favorites.route) {
                PlayerFavoritesScreen(navController = navController)
            }

            /* ───────────── PERFIL ───────────── */

            composable(Screen.Profile.route) {
                ProfileScreen(
                    isLoggedIn = isLoggedIn,
                    onLoginToggle = {
                        isLoggedIn = !isLoggedIn
                    }
                )
            }

            /* ───────────── ABOUT ───────────── */

            composable(Screen.About.route) {
                AboutScreen()
            }
        }
    }
}
