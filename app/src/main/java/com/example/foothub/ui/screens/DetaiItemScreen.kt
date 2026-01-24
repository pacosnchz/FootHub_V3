package com.example.foothub.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foothub.model.Player
import com.example.foothub.ui.components.FavoriteButton
import coil.compose.AsyncImage


@Composable
fun PlayerDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val player =
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<Player>("player")
            ?: return

    var isFavorite by remember { mutableStateOf(player.isFavorite) }

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
            .padding(16.dp)
    ) {

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                // Imagen del jugador
                AsyncImage(
                    model = player.photoUrl,
                    contentDescription = player.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )


                Spacer(modifier = Modifier.height(16.dp))

                // Nombre + favorito
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = player.name,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.weight(1f)
                    )

                    FavoriteButton(
                        isFavorite = isFavorite,
                        onClick = {
                            isFavorite = !isFavorite
                            // Se conectará al ViewModel más adelante
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Información del jugador
                Text(
                    text = "Equipo: ${player.team}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Posición: ${player.position}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
