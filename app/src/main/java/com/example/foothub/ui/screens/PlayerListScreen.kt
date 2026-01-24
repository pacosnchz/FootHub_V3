package com.example.foothub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foothub.model.Player
import com.example.foothub.repository.PlayerRepository
import com.example.foothub.ui.components.PlayerCard
import com.example.foothub.ui.components.FootHubWideHeader
import com.example.foothub.ui.navigation.Screen

@Composable
fun PlayerListScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val playerList = PlayerRepository.players

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

        // Lista principal (HOME)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = 88.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 96.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(playerList) { player ->
                PlayerCard(
                    name = player.name,
                    team = player.team,
                    position = player.position,
                    photoUrl = player.photoUrl,
                    isFavorite = player.isFavorite,
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

        // Header flotante (igual que WeAnime)
        FootHubWideHeader(
            title = "FootHub",
            onInfoClick = {
                navController.navigate(Screen.About.route)
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 20.dp)
                .padding(top = 12.dp)
        )
    }
}
