package com.example.foothub.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
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

    // ───── ESTADO GLOBAL ─────
    var playerList by remember { mutableStateOf(PlayerRepository.players) }
    var selectedPlayer by remember { mutableStateOf<Player?>(null) }
    var isLoggedIn by remember { mutableStateOf(false) }

    // ───── CONFIRMACIÓN GLOBAL ─────
    var playerPendingRemoval by remember { mutableStateOf<Player?>(null) }

    // ───── HANDLER ÚNICO DE FAVORITOS ─────
    val handleFavoriteAction: (FavoriteAction) -> Unit = { action ->
        when (action) {
            is FavoriteAction.Toggle -> {
                val player = action.player

                if (player.isFavorite) {
                    // 🔴 PEDIR CONFIRMACIÓN
                    playerPendingRemoval = player
                } else {
                    // 🟢 AÑADIR DIRECTO
                    playerList = playerList.map {
                        if (it.id == player.id)
                            it.copy(isFavorite = true)
                        else it
                    }
                }
            }
        }
    }

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

            /* ───────── LISTA ───────── */

            composable(Screen.List.route) {
                PlayerListScreen(
                    players = playerList,
                    onPlayerClick = { player ->
                        selectedPlayer = player
                        navController.navigate(Screen.Detail.route)
                    },
                    onFavoriteAction = handleFavoriteAction,
                    onInfoClick = {
                        navController.navigate(Screen.About.route)
                    }
                )
            }

            /* ───────── DETALLE ───────── */

            composable(Screen.Detail.route) {
                selectedPlayer?.let { player ->
                    PlayerDetailScreen(
                        player = player,
                        onFavoriteAction = handleFavoriteAction
                    )
                }
            }

            /* ───────── FAVORITOS ───────── */

            composable(Screen.Favorites.route) {
                PlayerFavoritesScreen(
                    players = playerList.filter { it.isFavorite },
                    onPlayerClick = { player ->
                        selectedPlayer = player
                        navController.navigate(Screen.Detail.route)
                    },
                    onFavoriteAction = handleFavoriteAction,
                    onInfoClick = {
                        navController.navigate(Screen.About.route)
                    }
                )
            }

            /* ───────── PERFIL ───────── */

            composable(Screen.Profile.route) {
                ProfileScreen(
                    isLoggedIn = isLoggedIn,
                    onLoginToggle = { isLoggedIn = !isLoggedIn }
                )
            }

            /* ───────── ABOUT ───────── */

            composable(Screen.About.route) {
                AboutScreen()
            }
        }
    }

    // ───────── ALERT DIALOG GLOBAL ─────────
    playerPendingRemoval?.let { player ->
        AlertDialog(
            onDismissRequest = { playerPendingRemoval = null },
            title = { Text("Quitar de favoritos") },
            text = { Text("¿Quieres eliminar a ${player.name} de favoritos?") },
            confirmButton = {
                TextButton(onClick = {
                    playerList = playerList.map {
                        if (it.id == player.id)
                            it.copy(isFavorite = false)
                        else it
                    }
                    playerPendingRemoval = null
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { playerPendingRemoval = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
