package com.example.tunisangoldenleague.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class Match(
    var homeTeam: Team,
    var awayTeam: Team,
    var homeTeamScore: Int,
    var awayTeamScore: Int,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime
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

    fun getScore(): String {
        return "$homeTeamScore - $awayTeamScore"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMatchDate(): String {
        return "${startDate.dayOfMonth} ${startDate.month} ${startDate.year}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMatchTime(): String {
        return "${startDate.hour.toString()}:${startDate.minute.toString()}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getElapsedTime(): String {
        if (endDate < LocalDateTime.now())
            return "Match Terminé"
        else {
            var minutes = ChronoUnit.MINUTES.between(startDate, LocalDateTime.now())
            var hours = ChronoUnit.HOURS.between(startDate, LocalDateTime.now())
            return "Temps Ecoulé : ${hours}h${minutes}mnt"
        }
    }
}
