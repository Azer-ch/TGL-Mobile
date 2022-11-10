package com.example.tunisangoldenleague.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDateTime

class Match (
    var homeTeam: Team,
    var awayTeam: Team,
    var homeTeamScore: Int,
    var awayTeamScore: Int,
    var date: LocalDateTime
) : Serializable {
    init {
        if (homeTeamScore > awayTeamScore) {
            homeTeam.points += 3
        } else if (homeTeamScore < awayTeamScore) {
            awayTeam.points += 3
        } else {
            homeTeam.points++
            awayTeam.points++
        }
    }
    fun getScore():String{
        return "$homeTeamScore - $awayTeamScore"
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getMatchDate():String{
        return "${date.dayOfMonth} ${date.month} ${date.year}"
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getMatchTime():String{
        return "${date.hour.toString()}:${date.minute.toString()}"
    }
}
