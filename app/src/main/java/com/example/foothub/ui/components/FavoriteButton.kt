package com.example.foothub.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Outlined.FavoriteBorder
            },
            contentDescription = "Marcar como favorito",
            tint = if (isFavorite) {
                Color(0xFFCC5E34) // Azul FootHub
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}
