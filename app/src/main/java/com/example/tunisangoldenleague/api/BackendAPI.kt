package com.example.tunisangoldenleague.api

import com.example.tunisangoldenleague.model.League
import com.example.tunisangoldenleague.model.Match
import com.example.tunisangoldenleague.model.Team
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BackendAPI {
    @GET("leagues")
    suspend fun  getLeagues(): Response<ArrayList<League>>
    @GET("teams?")
    fun getTeamsByLeague(@Query("league") league: String): Call<ArrayList<Team>>

    @GET("main")
    fun getRecentGames(): Call<ArrayList<Team>>

    @GET("league?")
    fun getMatchesByLeague(@Query("league") league: String): Call<ArrayList<Match>>

    @GET("live")
    fun getLiveMatches(): Call<ArrayList<Match>>
}