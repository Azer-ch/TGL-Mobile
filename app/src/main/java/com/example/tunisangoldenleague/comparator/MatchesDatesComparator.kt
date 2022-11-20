package com.example.tunisangoldenleague.comparator

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tunisangoldenleague.model.Match

class MatchesDatesComparator {

    companion object : Comparator<Match> {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun compare(a: Match, b: Match): Int = when {
            a.parseString(a.startDate).toLocalDate() != b.parseString(b.startDate).toLocalDate() -> b.parseString(b.startDate).toLocalDate().compareTo(a.parseString(a.startDate).toLocalDate())
            else -> b.parseString(b.startDate).toLocalTime().compareTo(a.parseString(a.startDate).toLocalTime())
        }
    }
}
