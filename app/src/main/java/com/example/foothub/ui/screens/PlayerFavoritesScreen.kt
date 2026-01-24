package com.example.foothub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foothub.repository.PlayerRepository
import com.example.foothub.ui.components.FootHubWideHeader
import com.example.foothub.ui.components.PlayerCard
import com.example.foothub.ui.navigation.Screen

@Composable
fun PlayerFavoritesScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    // De momento: favoritos simulados (estructura WeAnime)
    val favoriteList = PlayerRepository.players.filter { it.isFavorite }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE3F2FD), // Azul claro FootHub
                        Color.White
                    )
                )
            )
            .systemBarsPadding()
    ) {

        if (favoriteList.isEmpty()) {
            // Estado vacío
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No hay favoritos todavía",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Marca jugadores con el icono de favorito",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 88.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 24.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoriteList) { player ->
                    PlayerCard(
                        name = player.name,
                        team = player.team,
                        position = player.position,
                        photoUrl = player.photoUrl,
                        isFavorite = true,
                        onCardClick = {
                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("player", player)
                            navController.navigate(Screen.Detail.route)
                        },
                        onFavoriteClick = {
                            // Se conectará al ViewModel más adelante
                        }
                    )
                }
            }
        }

        // Header superior
        FootHubWideHeader(
            title = "Favoritos",
            onInfoClick = {},
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 20.dp)
                .padding(top = 12.dp)
        )
    }
}
