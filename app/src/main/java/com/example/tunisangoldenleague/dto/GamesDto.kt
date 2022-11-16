package com.example.tunisangoldenleague.dto

import com.example.tunisangoldenleague.model.Match

class GamesDto(var homeGames: ArrayList<Match>, var awayGames: ArrayList<Match>) {
    fun getAllMatches(): ArrayList<Match> {
        var result = ArrayList<Match>()
        this.homeGames.forEach { match -> result.add(match) }
        this.awayGames.forEach { match -> result.add(match) }
        return result
    }
}