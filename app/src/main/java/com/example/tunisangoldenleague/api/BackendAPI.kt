package com.example.tunisangoldenleague.api

import com.example.tunisangoldenleague.dto.GamesDto
import com.example.tunisangoldenleague.dto.PlayerDto
import com.example.tunisangoldenleague.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BackendAPI {
    @GET("leagues")
    fun getLeagues(): Call<ArrayList<League>>

    @GET("teams?")
    fun getTeamsByLeague(@Query("league") league: String): Call<ArrayList<Team>>

    @GET("main")
    fun getRecentGames(): Call<ArrayList<Team>>

    @GET("league?")
    fun getMatchesByLeague(@Query("league") league: String): Call<ArrayList<Match>>

    @GET("live")
    fun getLiveMatches(): Call<ArrayList<Match>>

    @GET("comments?")
    fun getEventsByMatch(@Query("game") game: String): Call<ArrayList<Event>>

    @GET("players?")
    fun getPlayersByTeamName(@Query("team") team: String): Call<ArrayList<Player>>

    @GET("games")
    fun getGamesByTeamId(@Query("team") team: String): Call<GamesDto>

    @GET("game")
    fun getMatchById(@Query("game") team: String): Call<Match>

    @GET("players")
    fun getPlayersByLeague(@Query("league") league: String): Call<ArrayList<PlayerDto>>
}