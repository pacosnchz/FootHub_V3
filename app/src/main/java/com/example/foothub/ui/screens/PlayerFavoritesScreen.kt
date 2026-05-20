package com.example.foothub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foothub.R
import com.example.foothub.model.Player
import com.example.foothub.ui.components.FootHubWideHeader
import com.example.foothub.ui.components.PlayerCard
import com.example.foothub.viewmodel.FavoritesViewModel

@Composable
fun PlayerFavoritesScreen(
    onPlayerClick: (Player) -> Unit,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val favorites by favoritesViewModel.favorites.collectAsStateWithLifecycle()

    // Jugador pendiente de confirmar borrado
    var playerPendingDelete by remember { mutableStateOf<Player?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFCC5E34), Color.White)))
            .systemBarsPadding()
    ) {
        if (favorites.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text  = stringResource(R.string.label_no_favorites),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text  = stringResource(R.string.hint_favorites_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 88.dp, start = 20.dp, end = 20.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favorites, key = { it.id }) { player ->
                    PlayerCard(
                        name            = player.name,
                        team            = player.team,
                        nationality     = player.nationality,
                        photoUrl        = player.photoUrl,
                        isFavorite      = true,
                        onCardClick     = { onPlayerClick(player) },
                        // Pulsar corazón en favoritos → pedir confirmación de borrado
                        onFavoriteClick = { playerPendingDelete = player }
                    )
                }
            }
        }

        FootHubWideHeader(
            title = stringResource(R.string.screen_favorites),
            onInfoClick = onInfoClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 20.dp)
                .padding(top = 12.dp)
        )
    }

    // Dialog de confirmación de borrado
    playerPendingDelete?.let { player ->
        AlertDialog(
            onDismissRequest = { playerPendingDelete = null },
            title  = { Text(stringResource(R.string.dialog_remove_favorite_title)) },
            text   = { Text(stringResource(R.string.dialog_remove_favorite_msg, player.name)) },
            confirmButton = {
                TextButton(onClick = {
                    favoritesViewModel.deleteFavorite(player)
                    playerPendingDelete = null
                }) { Text(stringResource(R.string.action_delete)) }
            },
            dismissButton = {
                TextButton(onClick = { playerPendingDelete = null }) {
                    Text(stringResource(R.string.action_cancel))
                }
            }
        )
    }
}