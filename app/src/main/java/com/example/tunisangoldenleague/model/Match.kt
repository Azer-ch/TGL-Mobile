package com.example.tunisangoldenleague.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.*

data class Match(
    var id: String,
    var homeTeam: Team,
    var awayTeam: Team,
    var homeTeamScore: Int,
    var awayTeamScore: Int,
    var startDate: String,
    var endDate: String
) : Serializable {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getScore(): String {
        val zoneId = ZoneId.of("Africa/Tunis")
        val now = ZonedDateTime.now(zoneId)
        if (parseString(startDate) > now)
            return "- : -"
        else
            return "$homeTeamScore - $awayTeamScore"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseString(date: String): ZonedDateTime {
        return ZonedDateTime.parse(date).withZoneSameInstant(ZoneId.of("Africa/Tunis"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMatchDate(): String {
        return "${parseString(startDate)?.dayOfMonth} ${
            parseString(startDate)?.month?.getDisplayName(
                TextStyle.FULL,
                Locale.FRANCE
            )
        } ${parseString(startDate)?.year}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMatchTime(): String {
        return parseString(startDate)?.toLocalTime().toString().substringBeforeLast(":")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getElapsedTime(): String {
        val zoneId = ZoneId.of("Africa/Tunis")
        val now = ZonedDateTime.now(zoneId)
        if (parseString(endDate) < now)
            return "Match Terminé"
        else {
            val duration = Duration.between(parseString(startDate), now)
            return "Temps Écoulé : ${duration.toMinutes().toString()} Minutes"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMatchDateV2(): String {
        return "${parseString(startDate).dayOfMonth}/${parseString(startDate).monthValue}/${parseString(startDate).year}"
    }
}

