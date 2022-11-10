package com.example.tunisangoldenleague

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tunisangoldenleague.adapters.MatchesAdapter
import com.example.tunisangoldenleague.enum.DivisionEnum
import com.example.tunisangoldenleague.model.Match
import com.example.tunisangoldenleague.model.Team
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var seniorMatchesList: RecyclerView
    lateinit var juniorMatchesList: RecyclerView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var teams = arrayListOf<Team>(
            Team("Club Africain", "ca", DivisionEnum.SENIOR, 0),
            Team("Esperance", "est", DivisionEnum.SENIOR, 0),
            Team("Shell", "shell", DivisionEnum.SENIOR, 0),
            Team("Tunisair", "tunisair", DivisionEnum.SENIOR, 0),
            Team("Club Africain", "ca", DivisionEnum.JUNIOR, 0),
            Team("Esperance", "est", DivisionEnum.JUNIOR, 0),
            Team("Shell", "shell", DivisionEnum.JUNIOR, 0),
            Team("Tunisair", "tunisair", DivisionEnum.JUNIOR, 0),
        )
        var matchesSenior = arrayListOf<Match>(
            Match(teams[0], teams[1], 4, 0, LocalDateTime.of(2022,8,10,11,30)),
            Match(teams[2], teams[3], 2, 2, LocalDateTime.of(2022, 10, 16, 15, 30))
        )
        var matchesJunior = arrayListOf<Match>(
            Match(teams[4], teams[5], 10, 0, LocalDateTime.of(2022,8,10,11,30)),
            Match(teams[6], teams[7], 1, 2, LocalDateTime.of(2022, 10, 16, 15, 30))
        )
        var seniorMatchesAdapter = MatchesAdapter(matchesSenior)
        var juniorMatchedAdapter = MatchesAdapter(matchesJunior)
        seniorMatchesList = findViewById(R.id.recyclerView)
        juniorMatchesList = findViewById(R.id.recyclerView1)
        seniorMatchesList.adapter = seniorMatchesAdapter
        juniorMatchesList.adapter = juniorMatchedAdapter
        seniorMatchesList.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
        )
        juniorMatchesList.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
        )
    }
}