package com.example.foothub.ui.navigation

import com.example.foothub.model.Player

sealed class FavoriteAction {
    data class Toggle(val player: Player) : FavoriteAction()
}
