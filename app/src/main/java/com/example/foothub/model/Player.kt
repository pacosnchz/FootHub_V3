package com.example.foothub.model

data class Player(
    val id: Int,
    val name: String,
    val team: String,
    val position: String,
    val age: Int,
    val nationality: String,
    val photoUrl: String,
    val isFavorite: Boolean = false
)
