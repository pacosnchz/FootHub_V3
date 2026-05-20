package com.example.foothub.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foothub.db.CommentEntity
import com.example.foothub.db.FootHubDatabase
import com.example.foothub.model.Player
import com.example.foothub.repository.PlayerRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PlayerRepository(
        FootHubDatabase.getInstance(application).favoriteDao()
    )

    /** Lista de favoritos en tiempo real (Flow → StateFlow). */
    val favorites: StateFlow<List<Player>> = repository.getFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    /** Comentarios del jugador seleccionado actualmente. */
    private val _selectedPlayerId = MutableStateFlow<Int?>(null)

    val comments: StateFlow<List<CommentEntity>> = _selectedPlayerId
        .filterNotNull()
        .flatMapLatest { repository.getComments(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun selectPlayer(playerId: Int) {
        _selectedPlayerId.value = playerId
    }

    // ─── Favoritos ────────────────────────────────────

    fun deleteFavorite(player: Player) {
        viewModelScope.launch { repository.deleteFavorite(player) }
    }

    // ─── Comentarios ──────────────────────────────────

    fun addComment(playerId: Int, author: String, text: String) {
        if (text.isBlank() || author.isBlank()) return
        viewModelScope.launch { repository.addComment(playerId, author, text) }
    }

    fun deleteComment(comment: CommentEntity) {
        viewModelScope.launch { repository.deleteComment(comment) }
    }
}