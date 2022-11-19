package com.example.tunisangoldenleague.comparator

import com.example.tunisangoldenleague.model.Team

class TeamsComparator {

    companion object : Comparator<Team> {

        override fun compare(a: Team, b: Team): Int = when {
            a.points != b.points -> b.points - a.points
            else -> (b.buts_marque-b.buts_encaisse) - (a.buts_marque-a.buts_encaisse)
        }
    }
}