package com.example.foothub.repository

import com.example.foothub.model.Player

object PlayerRepository {

    val players = listOf(
        Player(
            id = 1,
            name = "Antoine Griezmann",
            team = "Atlético de Madrid",
            position = "Media punta",
            age = 34,
            nationality = "Francia",
            photoUrl = "https://i.pinimg.com/736x/65/be/cf/65becf271631976692c662fe63e2dbbd.jpg"

        ),
        Player(
            id = 2,
            name = "Pablo Barrios",
            team = "Atlético de Madrid",
            position = "Mediocentro",
            age = 22,
            nationality = "España",
            photoUrl = "https://images.daznservices.com/di/library/DAZN_News/ea/7a/pablo-barrios_1nbh2iwwcbidc1jiqaf9fdv4dv.jpg?t=-1273513526"
        ),
        Player(
            id = 3,
            name = "Lionel Messi",
            team = "Inter Miami",
            position = "Delantero",
            age = 38,
            nationality = "Argentina",
            photoUrl = "https://e0.365dm.com/23/07/1600x900/skysports-lionel-messi-inter-miami_6230632.jpg?20230726111947"
        )
        // Puedes añadir más jugadores si quieres
    )
}
