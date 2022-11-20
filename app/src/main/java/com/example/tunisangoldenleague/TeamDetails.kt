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
    lateinit var equipe: TextView
    lateinit var playersDiv: ConstraintLayout
    lateinit var parent: ConstraintLayout
    lateinit var message : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)
        backArrow = findViewById(R.id.backArrow)
        playerRecyclerView = findViewById(R.id.recyclerView)
        matchDetailsRecyclerView = findViewById(R.id.recyclerView2)
        textView = findViewById(R.id.textView4)
        equipe = findViewById(R.id.textView5)
        playersDiv = findViewById(R.id.constraintLayout)
        parent = findViewById(R.id.parent)
        message = findViewById<TextView>(R.id.message)
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

    fun init(team: Team) {
        val backendApi = RetrofitHelper.getInstance().create(BackendAPI::class.java)
        var matches = ArrayList<Match>()
        // players list
        var layoutParam = matchDetailsRecyclerView.layoutParams as ConstraintLayout.LayoutParams
        var players =  ArrayList<Player>()
        var playersCall = backendApi.getPlayersByTeamName(team.name)
        playersCall!!.enqueue(object : Callback<ArrayList<Player>?> {
            override fun onResponse(
                call: Call<ArrayList<Player>?>,
                response: Response<ArrayList<Player>?>
            ) {
                if (response.isSuccessful) {
                    players = response.body()!!
                    players.add(Player("1","2","azer",30,30,30))
                    players.add(Player("1","2","azer chabbar",30,30,30))
                    if (players.isNotEmpty()) {
                        layoutParam.topToBottom = playersDiv.id
                        layoutParam.startToStart = parent.id
                        layoutParam.bottomToBottom = parent.id
                        message.visibility = View.INVISIBLE
                        matchDetailsRecyclerView.requestLayout()
                        equipe.visibility = View.VISIBLE
                        playersDiv.visibility = View.VISIBLE
                    } else {
                        if(matches.isEmpty())
                            message.visibility = View.VISIBLE
                        layoutParam.topToBottom = backArrow.id
                        layoutParam.startToStart = parent.id
                        layoutParam.bottomToBottom = parent.id
                        matchDetailsRecyclerView.requestLayout()
                        equipe.visibility = View.INVISIBLE
                        playersDiv.visibility = View.INVISIBLE
                    }
                    players.sortByDescending { player -> player.buts }
                    var playersAdapter = PlayersAdapter(players)
                    playerRecyclerView.adapter = playersAdapter
                    playerRecyclerView.layoutManager = LinearLayoutManager(this@TeamDetails)
                }
            }

            override fun onFailure(call: Call<ArrayList<Player>?>, t: Throwable) {
                layoutParam.topToBottom = backArrow.id
                layoutParam.startToStart = parent.id
                layoutParam.bottomToBottom = parent.id
                message.visibility = View.VISIBLE
                matchDetailsRecyclerView.requestLayout()
                equipe.visibility = View.INVISIBLE
                playersDiv.visibility = View.INVISIBLE
                Toast.makeText(this@TeamDetails, "Problème de connection...", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        // matches list
        var gamesDto: GamesDto
        var matchesCall = backendApi.getGamesByTeamId(team.id)
        matchesCall!!.enqueue(object : Callback<GamesDto> {
            override fun onResponse(
                call: Call<GamesDto>,
                response: Response<GamesDto>
            ) {
                if (response.isSuccessful) {
                    gamesDto = response.body()!!
                    matches = gamesDto.getAllMatches()
                    if(matches.isNotEmpty())
                        message.visibility = View.INVISIBLE
                    else{
                        if(players.isEmpty())
                            message.visibility = View.VISIBLE
                    }
                    matches.sortWith(MatchesDatesComparator)
                    val matchAdapter = MatchDetailsAdapter(matches)
                    matchDetailsRecyclerView.adapter = matchAdapter
                    matchDetailsRecyclerView.layoutManager = LinearLayoutManager(this@TeamDetails)
                }
            }

            override fun onFailure(call: Call<GamesDto>, t: Throwable) {
                if(matches.isEmpty() && players.isEmpty())
                    message.visibility = View.VISIBLE
                Toast.makeText(this@TeamDetails, "Problème de connection...", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}