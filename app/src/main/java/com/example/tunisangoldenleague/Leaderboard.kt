package com.example.tunisangoldenleague

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tunisangoldenleague.adapters.MatchDetailsAdapter
import com.example.tunisangoldenleague.adapters.MatchesAdapter
import com.example.tunisangoldenleague.adapters.PlayerDtoDetailsAdapter
import com.example.tunisangoldenleague.adapters.TeamsAdapter
import com.example.tunisangoldenleague.api.BackendAPI
import com.example.tunisangoldenleague.api.RetrofitHelper
import com.example.tunisangoldenleague.comparator.TeamsComparator
import com.example.tunisangoldenleague.dto.PlayerDto
import com.example.tunisangoldenleague.model.League
import com.example.tunisangoldenleague.model.Match
import com.example.tunisangoldenleague.model.Team
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Leaderboard : AppCompatActivity() {
    lateinit var backArrow: ImageView
    lateinit var leaderboardRecyclerView: RecyclerView
    lateinit var playersRecyclerView: RecyclerView
    lateinit var matchDetailsRecyclerView: RecyclerView
    lateinit var textView: TextView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var topStrikers : TextView
    lateinit var teamsDiv : ConstraintLayout
    lateinit var topStrikersDiv : ConstraintLayout
    lateinit var parent : ConstraintLayout
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
                    teams.sortWith(TeamsComparator)
                    val teamsAdapter = TeamsAdapter(teams)
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
        matchDetailsRecyclerView = findViewById(R.id.recyclerView2)
        var matches = ArrayList<Match>()
        var matchesCall = backendApi.getMatchesByLeague(league)
        matchesCall!!.enqueue(object : Callback<ArrayList<Match>?> {
            override fun onResponse(
                call: Call<ArrayList<Match>?>,
                response: Response<ArrayList<Match>?>
            ) {
                if (response.isSuccessful) {
                    matches = response.body()!!
                    val matchAdapter = MatchDetailsAdapter(matches)
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
        // players creation
        var players = ArrayList<PlayerDto>()
        var playersCall = backendApi.getPlayersByLeague(league)
        topStrikers = findViewById(R.id.textView30)
        topStrikersDiv = findViewById(R.id.constraintLayout2)
        teamsDiv = findViewById(R.id.constraintLayout)
        parent = findViewById(R.id.parent)
        var layoutParam = matchDetailsRecyclerView.layoutParams as ConstraintLayout.LayoutParams
        playersCall!!.enqueue(object : Callback<ArrayList<PlayerDto>?> {
            override fun onResponse(
                call: Call<ArrayList<PlayerDto>?>,
                response: Response<ArrayList<PlayerDto>?>
            ) {
                if (response.isSuccessful) {
                    players = response.body()!!
                    if(players.isNotEmpty()) {
                        layoutParam.topToBottom = topStrikersDiv.id
                        layoutParam.startToStart = parent.id
                        layoutParam.bottomToBottom = parent.id
                        matchDetailsRecyclerView.requestLayout()
                        topStrikers.visibility = View.VISIBLE
                        topStrikersDiv.visibility = View.VISIBLE
                    }
                    else{
                        layoutParam.topToBottom = teamsDiv.id
                        layoutParam.startToStart = parent.id
                        layoutParam.bottomToBottom = parent.id
                        matchDetailsRecyclerView.requestLayout()
                        topStrikers.visibility = View.INVISIBLE
                        topStrikersDiv.visibility = View.INVISIBLE
                    }
                    players.sortByDescending { playerDto -> playerDto.buts }
                    val playersAdapter = PlayerDtoDetailsAdapter(players)
                    playersRecyclerView = findViewById(R.id.recyclerView3)
                    playersRecyclerView.adapter = playersAdapter
                    playersRecyclerView.layoutManager = LinearLayoutManager(this@Leaderboard)
                }
            }

            override fun onFailure(call: Call<ArrayList<PlayerDto>?>, t: Throwable) {
                layoutParam.topToBottom = teamsDiv.id
                layoutParam.startToStart = parent.id
                layoutParam.bottomToBottom = parent.id
                matchDetailsRecyclerView.requestLayout()
                topStrikers.visibility = View.INVISIBLE
                topStrikersDiv.visibility = View.INVISIBLE
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