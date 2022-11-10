package com.example.tunisangoldenleague

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tunisangoldenleague.adapters.MatchDetailsAdapter
import com.example.tunisangoldenleague.adapters.TeamsAdapter
import com.example.tunisangoldenleague.model.Match
import com.example.tunisangoldenleague.model.Team

class Leaderboard : AppCompatActivity() {
    lateinit var backArrow: ImageView
    lateinit var leaderboardRecyclerView: RecyclerView
    lateinit var matchDetailsRecyclerView: RecyclerView
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)
        var teams = intent.getSerializableExtra("teams") as ArrayList<Team>
        var matches = intent.getSerializableExtra("matches") as ArrayList<Match>
        matches.forEach { team -> println(team.date) }
        var division = intent.getStringExtra("division")
        backArrow = findViewById(R.id.backArrow)
        leaderboardRecyclerView = findViewById(R.id.recyclerView)
        matchDetailsRecyclerView = findViewById(R.id.recyclerView2)
        textView = findViewById(R.id.textView4)
        textView.setText(division)
        var teamsAdapter = TeamsAdapter(teams)
        var matchAdapter = MatchDetailsAdapter(matches)
        leaderboardRecyclerView.adapter = teamsAdapter
        leaderboardRecyclerView.layoutManager = LinearLayoutManager(this)
        matchDetailsRecyclerView.adapter = matchAdapter
        matchDetailsRecyclerView.layoutManager = LinearLayoutManager(this)
        backArrow.setOnClickListener {
            finish()
        }
    }
}