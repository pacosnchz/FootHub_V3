package com.example.foothub.api

import com.google.gson.annotations.SerializedName

/** Respuesta del endpoint /competitions/{id}/scorers */
data class TopScorersResponse(
    @SerializedName("scorers") val scorers: List<ScorerDto>
)

data class ScorerDto(
    @SerializedName("player")      val player:      PlayerDto,
    @SerializedName("team")        val team:        TeamDto,
    @SerializedName("goals")       val goals:       Int?,
    @SerializedName("assists")     val assists:     Int?,
    @SerializedName("penalties")   val penalties:   Int?
)

data class PlayerDto(
    @SerializedName("id")          val id:          Int,
    @SerializedName("name")        val name:        String,
    @SerializedName("position")    val position:    String?,
    @SerializedName("nationality") val nationality: String?,
    @SerializedName("dateOfBirth") val dateOfBirth: String?,
    @SerializedName("section")     val section:     String?
)

data class TeamDto(
    @SerializedName("id")          val id:    Int,
    @SerializedName("name")        val name:  String,
    @SerializedName("crest")       val crest: String?
)