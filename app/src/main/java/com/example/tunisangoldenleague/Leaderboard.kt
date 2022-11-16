package com.example.tunisangoldenleague

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tunisangoldenleague.adapters.MatchDetailsAdapter
import com.example.tunisangoldenleague.adapters.MatchesAdapter
import com.example.tunisangoldenleague.adapters.TeamsAdapter
import com.example.tunisangoldenleague.api.BackendAPI
import com.example.tunisangoldenleague.api.RetrofitHelper
import com.example.tunisangoldenleague.model.League
import com.example.tunisangoldenleague.model.Match
import com.example.tunisangoldenleague.model.Team
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Leaderboard : AppCompatActivity() {
    lateinit var backArrow: ImageView
    lateinit var leaderboardRecyclerView: RecyclerView
    lateinit var matchDetailsRecyclerView: RecyclerView
    lateinit var textView: TextView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)
        var league = intent.getStringExtra("title")
        backArrow = findViewById(R.id.backArrow)
        textView = findViewById(R.id.textView4)
        textView.setText(league)
        backArrow.setOnClickListener {
            finish()
        }
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            if (league != null) {
                init(league)
            }
            swipeRefreshLayout.isRefreshing = false
        }
        if (league != null) {
            init(league)
        }
    }

    fun init(league: String) {
        val backendApi = RetrofitHelper.getInstance().create(BackendAPI::class.java)
        // teams creation
        var teams: ArrayList<Team>
        var teamsCall = backendApi.getTeamsByLeague(league)
        teamsCall!!.enqueue(object : Callback<ArrayList<Team>?> {
            override fun onResponse(
                call: Call<ArrayList<Team>?>,
                response: Response<ArrayList<Team>?>
            ) {
                if (response.isSuccessful) {
                    teams = response.body()!!
                    var teamsAdapter = TeamsAdapter(teams)
                    leaderboardRecyclerView = findViewById(R.id.recyclerView)
                    leaderboardRecyclerView.adapter = teamsAdapter
                    leaderboardRecyclerView.layoutManager = LinearLayoutManager(this@Leaderboard)
                }
            }

            override fun onFailure(call: Call<ArrayList<Team>?>, t: Throwable) {
                Toast.makeText(
                    this@Leaderboard,
                    "Problème de connection...",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
        // matches creation
        var matches = ArrayList<Match>()
        var matchesCall = backendApi.getMatchesByLeague(league)
        matchesCall!!.enqueue(object : Callback<ArrayList<Match>?> {
            override fun onResponse(
                call: Call<ArrayList<Match>?>,
                response: Response<ArrayList<Match>?>
            ) {
                if (response.isSuccessful) {
                    matches = response.body()!!
                    var matchAdapter = MatchDetailsAdapter(matches)
                    matchDetailsRecyclerView = findViewById(R.id.recyclerView2)
                    matchDetailsRecyclerView.adapter = matchAdapter
                    matchDetailsRecyclerView.layoutManager = LinearLayoutManager(this@Leaderboard)
                }
            }

            override fun onFailure(call: Call<ArrayList<Match>?>, t: Throwable) {
                Toast.makeText(
                    this@Leaderboard,
                    "Problème de connection...",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }
}