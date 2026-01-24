package com.example.foothub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
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
fun PlayerListScreen(
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
                    colors = listOf(
                        Color(0xFFCC5E34),
                        Color.White
                    )
                )
            )
            .systemBarsPadding()
    ) {

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
            items(players) { player ->
                PlayerCard(
                    name = player.name,
                    team = player.team,
                    position = player.position,
                    photoUrl = player.photoUrl,
                    isFavorite = player.isFavorite,
                    onCardClick = {
                        onPlayerClick(player)
                    },
                    onFavoriteClick = {
                        if (player.isFavorite) {
                            onFavoriteAction(FavoriteAction.RequestRemove(player))
                        } else {
                            onFavoriteAction(FavoriteAction.Toggle(player))
                        }
                    }

                )
            }
        }

        FootHubWideHeader(
            title = "FootHub",
            onInfoClick = onInfoClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 20.dp)
                .padding(top = 12.dp)
        )
    }
}
