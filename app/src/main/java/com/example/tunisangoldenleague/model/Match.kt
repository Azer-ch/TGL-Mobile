package com.example.tunisangoldenleague.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

data class Match(
    var homeTeam: Team,
    var awayTeam: Team,
    var homeTeamScore: Int,
    var awayTeamScore: Int,
    var startDate: String,
    var endDate: String
) : Serializable {
    fun getScore(): String {
        return "$homeTeamScore - $awayTeamScore"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseString(date: String): LocalDateTime {
        return LocalDateTime.parse(
            date,
            DateTimeFormatter.
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMatchDate(): String {
        return "${parseString(startDate)?.dayOfMonth} ${parseString(startDate)?.month} ${parseString(startDate)?.year}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMatchTime(): String {
        return parseString(startDate)?.toLocalTime().toString()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getElapsedTime(): String {
        if (parseString(endDate) < LocalDateTime.now())
            return "Match Terminé"
        else {
            var minutes = ChronoUnit.MINUTES.between(parseString(startDate), LocalDateTime.now()) % 60
            var hours = ChronoUnit.HOURS.between(parseString(startDate), LocalDateTime.now()) + ChronoUnit.MINUTES.between(parseString(startDate), LocalDateTime.now()) /60
            return "Temps Ecoulé : ${hours}h${minutes}mnt"
        }
    }
}

