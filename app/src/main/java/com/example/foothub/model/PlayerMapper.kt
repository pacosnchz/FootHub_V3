package com.example.foothub.model

import com.example.foothub.api.ScorerDto
import com.example.foothub.db.FavoriteEntity

/**
 * Convierte un ScorerDto de la API en un Player de dominio.
 * photoUrl usa el escudo del equipo (la API gratuita no da foto de jugador).
 */
fun ScorerDto.toPlayer(isFavorite: Boolean = false) = Player(
    id          = player.id,
    name        = player.name,
    team        = team.name,
    position    = player.position ?: "Desconocida",
    nationality = player.nationality ?: "Desconocida",
    photoUrl    = team.crest ?: "",
    dateOfBirth = player.dateOfBirth ?: "",
    goals       = goals ?: 0,
    assists     = assists ?: 0,
    penalties   = penalties ?: 0,
    isFavorite  = isFavorite
)

/** Convierte un Player de dominio en una FavoriteEntity para guardarlo en Room. */
fun Player.toFavoriteEntity() = FavoriteEntity(
    id          = id,
    name        = name,
    team        = team,
    position    = position,
    nationality = nationality,
    photoUrl    = photoUrl,
    goals       = goals,
    assists     = assists
)

/** Convierte una FavoriteEntity de Room en un Player de dominio. */
fun FavoriteEntity.toPlayer() = Player(
    id          = id,
    name        = name,
    team        = team,
    position    = position,
    nationality = nationality,
    photoUrl    = photoUrl,
    goals       = goals,
    assists     = assists,
    isFavorite  = true
)