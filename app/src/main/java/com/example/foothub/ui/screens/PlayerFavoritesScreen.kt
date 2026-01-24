package com.example.foothub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.foothub.model.Player
import com.example.foothub.ui.components.FootHubWideHeader
import com.example.foothub.ui.components.PlayerCard
import com.example.foothub.ui.navigation.FavoriteAction

@Composable
fun PlayerFavoritesScreen(
    players: List<Player>,
    onPlayerClick: (Player) -> Unit,
    onFavoriteAction: (FavoriteAction) -> Unit,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFCC5E34),
                        Color.White
                    )
                )
            )
            .systemBarsPadding()
    ) {

        if (players.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No hay favoritos todavía",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Marca jugadores con el corazón",
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
                items(players) { player ->
                    PlayerCard(
                        name = player.name,
                        team = player.team,
                        nationality = player.nationality,
                        photoUrl = player.photoUrl,
                        isFavorite = true,
                        onCardClick = {
                            onPlayerClick(player)
                        },
                        onFavoriteClick = {
                            // MISMA ACCIÓN QUE EN TODAS LAS PANTALLAS
                            onFavoriteAction(FavoriteAction.Toggle(player))
                        }
                    )
                }
            }
        }

        FootHubWideHeader(
            title = "Favoritos",
            onInfoClick = onInfoClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 20.dp)
                .padding(top = 12.dp)
        )
    }
}
