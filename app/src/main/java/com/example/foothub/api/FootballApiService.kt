package com.example.foothub.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FootballApiService {

    /**
     * Máximos goleadores de una competición.
     * competition: PL (Premier League), PD (LaLiga), BL1 (Bundesliga), SA (Serie A)...
     */
    @GET("competitions/{competition}/scorers")
    suspend fun getTopScorers(
        @Path("competition") competition: String = "PL",
        @Query("limit")      limit: Int = 150
    ): TopScorersResponse
}