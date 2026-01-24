package com.example.foothub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
    var query by remember { mutableStateOf("") }

    val filteredPlayers = players.filter {
        it.name.contains(query, ignoreCase = true) ||
                it.team.contains(query, ignoreCase = true) ||
                it.nationality.contains(query, ignoreCase = true)
    }

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
        Column {
            // HEADER (sin cambios)
            FootHubWideHeader(
                title = "FootHub",
                onInfoClick = onInfoClick,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 12.dp)
            )

            // 🔍 SEARCH BAR (CON CAMBIOS)
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 12.dp),
                placeholder = {
                    Text("Buscar jugador, equipo o país")
                },
                singleLine = true,
                // --- INICIO DE LOS CAMBIOS ---
                // 1. Poner el texto que se escribe en negrita
                textStyle = TextStyle(fontWeight = FontWeight.Bold),
                colors = OutlinedTextFieldDefaults.colors(
                    // 2. Hacer el fondo opaco (blanco)
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                )
                // --- FIN DE LOS CAMBIOS ---
            )

            Spacer(modifier = Modifier.height(12.dp))

            // LISTA (sin cambios)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 96.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredPlayers) { player ->
                    PlayerCard(
                        name = player.name,
                        team = player.team,
                        nationality = player.nationality,
                        photoUrl = player.photoUrl,
                        isFavorite = player.isFavorite,
                        onCardClick = {
                            onPlayerClick(player)
                        },
                        onFavoriteClick = {
                            onFavoriteAction(FavoriteAction.Toggle(player))
                        }
                    )
                }
            }
        }
    }
}
