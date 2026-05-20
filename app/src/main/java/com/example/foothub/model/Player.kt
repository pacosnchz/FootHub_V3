package com.example.foothub.model

data class Player(
    val id:          Int,
    val name:        String,
    val team:        String,
    val position:    String,
    val nationality: String,
    val photoUrl:    String,
    val dateOfBirth: String  = "",
    val goals:       Int     = 0,
    val assists:     Int     = 0,
    val penalties:   Int     = 0,
    val isFavorite:  Boolean = false
)