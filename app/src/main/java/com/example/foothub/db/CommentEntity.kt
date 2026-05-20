package com.example.foothub.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) val commentId: Int = 0,
    val playerId:  Int,    // FK lógica hacia FavoriteEntity.id
    val author:    String, // nombre del usuario que comenta
    val text:      String,
    val timestamp: Long = System.currentTimeMillis()
)