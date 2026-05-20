package com.example.foothub.repository

import com.example.foothub.api.RetrofitClient
import com.example.foothub.db.CommentEntity
import com.example.foothub.db.FavoriteDao
import com.example.foothub.model.Player
import com.example.foothub.model.toFavoriteEntity
import com.example.foothub.model.toPlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}

class PlayerRepository(private val dao: FavoriteDao) {

    // ───── API ────────────────────────────────────────────

    /**
     * Obtiene los goleadores de una competición y marca
     * cuáles ya están guardados como favoritos en Room.
     */
    suspend fun fetchPlayers(competition: String = "PL"): ApiResult<List<Player>> {
        return try {
            val favoriteIds = dao.getAllFavoriteIds().first().toSet()
            val response = RetrofitClient.service.getTopScorers(competition)
            val players = response.scorers.map { dto ->
                dto.toPlayer(isFavorite = dto.player.id in favoriteIds)
            }
            ApiResult.Success(players)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Error al obtener datos de la API")
        }
    }

    // ───── FAVORITOS (Room) ──────────────────────────────

    /** Flow en tiempo real de todos los favoritos guardados. */
    fun getFavorites(): Flow<List<Player>> =
        dao.getAllFavorites().map { list -> list.map { it.toPlayer() } }

    /** IDs de favoritos como Flow para marcar jugadores en la lista. */
    fun getFavoriteIds(): Flow<Set<Int>> =
        dao.getAllFavoriteIds().map { it.toSet() }

    suspend fun saveFavorite(player: Player) {
        dao.insertFavorite(player.toFavoriteEntity())
    }

    suspend fun deleteFavorite(player: Player) {
        dao.deleteFavorite(player.toFavoriteEntity())
    }

    suspend fun isFavorite(playerId: Int): Boolean =
        dao.isFavorite(playerId)

    // ───── COMENTARIOS (Room) ────────────────────────────

    fun getComments(playerId: Int): Flow<List<CommentEntity>> =
        dao.getCommentsForPlayer(playerId)

    suspend fun addComment(playerId: Int, author: String, text: String) {
        dao.insertComment(
            CommentEntity(playerId = playerId, author = author, text = text)
        )
    }

    suspend fun deleteComment(comment: CommentEntity) {
        dao.deleteComment(comment)
    }
}