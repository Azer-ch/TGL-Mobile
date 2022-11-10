package com.example.tunisangoldenleague

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
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
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var seniorMatchesList: RecyclerView
    lateinit var juniorMatchesList: RecyclerView
    lateinit var junior: TextView
    lateinit var senior: TextView

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
        var seniorMatches = arrayListOf<Match>(
            Match(teams[0], teams[1], 4, 0, LocalDateTime.of(2022, 8, 10, 11, 30)),
            Match(teams[2], teams[3], 2, 2, LocalDateTime.of(2022, 10, 16, 15, 30))
        )
        var juniorMatches = arrayListOf<Match>(
            Match(teams[4], teams[5], 10, 0, LocalDateTime.of(2022, 8, 10, 11, 30)),
            Match(teams[6], teams[7], 1, 2, LocalDateTime.of(2022, 10, 16, 15, 30))
        )
        var seniorTeams = ArrayList<Team>()
        var juniorTeams = ArrayList<Team>()
        teams.forEach { team ->
            if(team.division == DivisionEnum.SENIOR)
                seniorTeams.add(team)
            else
                juniorTeams.add(team)
        }
        seniorTeams.sortByDescending { team -> team.points }
        juniorTeams.sortByDescending { team -> team.points }
        var seniorMatchesAdapter = MatchesAdapter(seniorMatches)
        var juniorMatchedAdapter = MatchesAdapter(juniorMatches)
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
        senior = findViewById(R.id.textView)
        junior = findViewById(R.id.textView1)
        senior.setOnClickListener{
            var intent = Intent(this@MainActivity,Leaderboard::class.java)
            intent.putExtra("division","Championnat Senior")
            intent.putExtra("teams", seniorTeams)
            intent.putExtra("matches",seniorMatches)
            startActivity(intent)
        }
        junior.setOnClickListener{
            var intent = Intent(this@MainActivity,Leaderboard::class.java)
            intent.putExtra("division","Championnat Junior")
            intent.putExtra("teams", juniorTeams)
            intent.putExtra("matches",juniorMatches)
            startActivity(intent)
        }
    }
}