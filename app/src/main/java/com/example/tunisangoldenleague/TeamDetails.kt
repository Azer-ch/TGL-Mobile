package com.example.tunisangoldenleague

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tunisangoldenleague.adapters.MatchDetailsAdapter
import com.example.tunisangoldenleague.adapters.MatchesAdapter
import com.example.tunisangoldenleague.adapters.PlayersAdapter
import com.example.tunisangoldenleague.adapters.TeamsAdapter
import com.example.tunisangoldenleague.api.BackendAPI
import com.example.tunisangoldenleague.api.RetrofitHelper
import com.example.tunisangoldenleague.comparator.MatchesDatesComparator
import com.example.tunisangoldenleague.dto.GamesDto
import com.example.tunisangoldenleague.model.Match
import com.example.tunisangoldenleague.model.Player
import com.example.tunisangoldenleague.model.Team
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamDetails : AppCompatActivity() {
    lateinit var backArrow: ImageView
    lateinit var playerRecyclerView: RecyclerView
    lateinit var matchDetailsRecyclerView: RecyclerView
    lateinit var textView: TextView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)
        backArrow = findViewById(R.id.backArrow)
        playerRecyclerView = findViewById(R.id.recyclerView)
        matchDetailsRecyclerView = findViewById(R.id.recyclerView2)
        textView = findViewById(R.id.textView4)
        var team = intent.getSerializableExtra("team") as Team
        textView.setText(team.name)
        init(team)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            init(team)
            swipeRefreshLayout.isRefreshing = false
        }
        backArrow.setOnClickListener {
            finish()
        }
    }
    fun init(team : Team){
        val backendApi = RetrofitHelper.getInstance().create(BackendAPI::class.java)
        // players list
        var players: ArrayList<Player>
        var playersCall = backendApi.getPlayersByTeamName(team.name)
        playersCall!!.enqueue(object : Callback<ArrayList<Player>?> {
            override fun onResponse(
                call: Call<ArrayList<Player>?>,
                response: Response<ArrayList<Player>?>
            ) {
                if (response.isSuccessful) {
                    players = response.body()!!
                    players.sortByDescending { player -> player.buts }
                    var playersAdapter = PlayersAdapter(players)
                    playerRecyclerView.adapter = playersAdapter
                    playerRecyclerView.layoutManager = LinearLayoutManager(this@TeamDetails)
                }
            }

            override fun onFailure(call: Call<ArrayList<Player>?>, t: Throwable) {
                Toast.makeText(this@TeamDetails, "Problème de connection...", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        // matches list
        var gamesDto : GamesDto
        var matchesCall  = backendApi.getGamesByTeamId(team.id)
        matchesCall!!.enqueue(object : Callback<GamesDto> {
            override fun onResponse(
                call: Call<GamesDto>,
                response: Response<GamesDto>
            ) {
                if (response.isSuccessful) {
                    gamesDto = response.body()!!
                    val matches = gamesDto.getAllMatches()
                    matches.sortWith(MatchesDatesComparator)
                    val matchAdapter = MatchDetailsAdapter(matches)
                    matchDetailsRecyclerView.adapter = matchAdapter
                    matchDetailsRecyclerView.layoutManager = LinearLayoutManager(this@TeamDetails)
                }
            }

            override fun onFailure(call: Call<GamesDto>, t: Throwable) {
                Toast.makeText(this@TeamDetails, "Problème de connection...", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}