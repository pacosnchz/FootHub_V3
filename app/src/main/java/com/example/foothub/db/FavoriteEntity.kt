package com.example.foothub.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: Int,
    val name:        String,
    val team:        String,
    val position:    String,
    val nationality: String,
    val photoUrl:    String,
    val goals:       Int = 0,
    val assists:     Int = 0
)