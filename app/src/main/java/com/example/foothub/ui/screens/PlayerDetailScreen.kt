package com.example.foothub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.foothub.model.Player
import com.example.foothub.ui.components.FavoriteButton
import com.example.foothub.ui.navigation.FavoriteAction

@Composable
fun PlayerDetailScreen(
    player: Player,
    onFavoriteAction: (FavoriteAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // ───────── MITAD SUPERIOR: IMAGEN ─────────
        AsyncImage(
            model = player.photoUrl,
            contentDescription = player.name,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentScale = ContentScale.Crop
        )

        // ───────── MITAD INFERIOR: INFO ─────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(20.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.weight(1f)
                )

                FavoriteButton(
                    isFavorite = player.isFavorite,
                    onClick = {
                        if (player.isFavorite) {
                            onFavoriteAction(FavoriteAction.RequestRemove(player))
                        } else {
                            onFavoriteAction(FavoriteAction.Toggle(player))
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Equipo: ${player.team}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )

            Text(
                text = "Posición: ${player.position}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )

            Text(
                text = "Edad: ${player.age} años",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )

            Text(
                text = "Nacionalidad: ${player.nationality}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }
    }
}
