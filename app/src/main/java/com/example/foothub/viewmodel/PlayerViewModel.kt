package com.example.foothub.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foothub.db.FootHubDatabase
import com.example.foothub.model.Player
import com.example.foothub.repository.ApiResult
import com.example.foothub.repository.PlayerRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class PlayerUiState(
    val players:    List<Player> = emptyList(),
    val isLoading:  Boolean      = false,
    val errorMsg:   String?      = null
)

class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PlayerRepository(
        FootHubDatabase.getInstance(application).favoriteDao()
    )

    private val _uiState = MutableStateFlow(PlayerUiState(isLoading = true))
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    /** IDs favoritos en tiempo real para marcar el icono corazón. */
    val favoriteIds: StateFlow<Set<Int>> = repository.getFavoriteIds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptySet())

    init {
        loadPlayers()
    }

    fun loadPlayers(competition: String = "PL") {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMsg = null) }
            when (val result = repository.fetchPlayers(competition)) {
                is ApiResult.Success -> {
                    // Combina el resultado de la API con los IDs de favoritos actuales
                    val ids = favoriteIds.value
                    val withFav = result.data.map { p -> p.copy(isFavorite = p.id in ids) }
                    _uiState.update { it.copy(players = withFav, isLoading = false) }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMsg = result.message) }
                }
                else -> Unit
            }
        }
    }

    /** Guarda o muestra toast según si ya es favorito. */
    fun toggleFavorite(player: Player, onAlreadySaved: () -> Unit) {
        viewModelScope.launch {
            if (repository.isFavorite(player.id)) {
                onAlreadySaved()
            } else {
                repository.saveFavorite(player)
                // Actualizar lista en memoria para reflejar el cambio
                _uiState.update { state ->
                    state.copy(
                        players = state.players.map {
                            if (it.id == player.id) it.copy(isFavorite = true) else it
                        }
                    )
                }
            }
        }
    }
}