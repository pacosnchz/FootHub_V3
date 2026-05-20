package com.example.foothub.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    // ───── FAVORITOS ─────────────────────────────────────

    @Query("SELECT * FROM favorites ORDER BY name ASC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT id FROM favorites")
    fun getAllFavoriteIds(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :playerId)")
    suspend fun isFavorite(playerId: Int): Boolean

    // ───── COMENTARIOS ────────────────────────────────────

    @Query("SELECT * FROM comments WHERE playerId = :playerId ORDER BY timestamp ASC")
    fun getCommentsForPlayer(playerId: Int): Flow<List<CommentEntity>>

    @Insert
    suspend fun insertComment(comment: CommentEntity)

    @Delete
    suspend fun deleteComment(comment: CommentEntity)
}