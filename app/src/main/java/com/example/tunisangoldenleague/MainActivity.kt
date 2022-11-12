package com.example.tunisangoldenleague

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tunisangoldenleague.adapters.MatchesAdapter
import com.example.tunisangoldenleague.api.BackendAPI
import com.example.tunisangoldenleague.api.RetrofitHelper
import com.example.tunisangoldenleague.enum.DivisionEnum
import com.example.tunisangoldenleague.model.Match
import com.example.tunisangoldenleague.model.Team
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var tglMatchesList: RecyclerView
    lateinit var tglP35MatchesList: RecyclerView
    lateinit var tflMatchesList: RecyclerView
    lateinit var liveMatchesList: RecyclerView
    lateinit var tgl: TextView
    lateinit var tglP35: TextView
    lateinit var tfl: TextView
    lateinit var live: ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var liveMatches = ArrayList<Match>()
        var tglP35Matches = ArrayList<Match>()
        var tglMatches = ArrayList<Match>()
        var tflMatches = ArrayList<Match>()
        var textViews = ArrayList<TextView>()
        val backendApi = RetrofitHelper.getInstance().create(BackendAPI::class.java)
        val liveMatchesCall = backendApi.getLiveMatches() as Call<ArrayList<Match>?>?
        val tglMatchesCall =
            backendApi.getMatchesByLeague("Tunisia Golden League") as Call<ArrayList<Match>?>?
        val tglP35MatchesCall =
            backendApi.getMatchesByLeague("Tunisia Golden League P35") as Call<ArrayList<Match>?>?
        val tflMatchesCall =
            backendApi.getMatchesByLeague("Tunisia First League") as Call<ArrayList<Match>?>?
        live = findViewById(R.id.imageView0)
        liveMatchesList = findViewById(R.id.recyclerView0)
        tglP35MatchesList = findViewById(R.id.recyclerView)
        tglMatchesList = findViewById(R.id.recyclerView1)
        tflMatchesList = findViewById(R.id.recyclerView2)
        tglP35 = findViewById(R.id.textView)
        tgl = findViewById(R.id.textView1)
        tfl = findViewById(R.id.textView2)
        textViews.add(findViewById(R.id.textView14))
        textViews.add(findViewById(R.id.textView15))
        textViews.add(findViewById(R.id.textView16))
        textViews.add(findViewById(R.id.textView17))
        liveMatchesCall!!.enqueue(object : Callback<ArrayList<Match>?> {
            override fun onResponse(
                call: Call<ArrayList<Match>?>,
                response: Response<ArrayList<Match>?>
            ) {
                if (response.isSuccessful) {
                    liveMatches = response.body()!!
                    if (!liveMatches.isEmpty()) {
                        textViews[0].visibility = View.INVISIBLE
                        var liveMatchesAdapter = MatchesAdapter(liveMatches)
                        liveMatchesList.adapter = liveMatchesAdapter
                        liveMatchesList.setLayoutManager(
                            LinearLayoutManager(
                                this@MainActivity,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                        )
                    }
                    live.setOnClickListener {
                        var intent = Intent(this@MainActivity, LiveMatches::class.java)
                        intent.putExtra("matches", liveMatches)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Match>?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Problème Serveur...", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        tglP35MatchesCall!!.enqueue(object : Callback<ArrayList<Match>?> {
            override fun onResponse(
                call: Call<ArrayList<Match>?>,
                response: Response<ArrayList<Match>?>
            ) {
                if (response.isSuccessful) {
                    tglP35Matches = response.body()!!
                    if (!tglP35Matches.isEmpty()) {
                        textViews[1].visibility = View.INVISIBLE
                        var tglP35MatchesAdapter = MatchesAdapter(tglP35Matches)
                        tglP35MatchesList.adapter = tglP35MatchesAdapter
                        tglP35MatchesList.setLayoutManager(
                            LinearLayoutManager(
                                this@MainActivity,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Match>?>, t: Throwable) {
                // displaying an error message in toast
                Toast.makeText(this@MainActivity, "Problème Serveur...", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        tglMatchesCall!!.enqueue(object : Callback<ArrayList<Match>?> {
            override fun onResponse(
                call: Call<ArrayList<Match>?>,
                response: Response<ArrayList<Match>?>
            ) {
                if (response.isSuccessful) {
                    tglMatches = response.body()!!
                    if (!tglMatches.isEmpty()) {
                        textViews[2].visibility = View.INVISIBLE
                        var tglMatchesAdapter = MatchesAdapter(tglMatches)
                        tglMatchesList.adapter = tglMatchesAdapter
                        tglMatchesList.setLayoutManager(
                            LinearLayoutManager(
                                this@MainActivity,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Match>?>, t: Throwable) {
                // displaying an error message in toast
                Toast.makeText(this@MainActivity, "Problème Serveur...", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        tflMatchesCall!!.enqueue(object : Callback<ArrayList<Match>?> {
            override fun onResponse(
                call: Call<ArrayList<Match>?>,
                response: Response<ArrayList<Match>?>
            ) {
                if (response.isSuccessful) {
                    tflMatches = response.body()!!
                    if (!tflMatches.isEmpty()) {
                        textViews[3].visibility = View.INVISIBLE
                        var tflMatchesAdapter = MatchesAdapter(tflMatches)
                        tflMatchesList.adapter = tflMatchesAdapter
                        tflMatchesList.setLayoutManager(
                            LinearLayoutManager(
                                this@MainActivity,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Match>?>, t: Throwable) {
                // displaying an error message in toast
                Toast.makeText(this@MainActivity, "Problème Serveur...", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        val tglTeamsCall =
            backendApi.getTeamsByLeague("Tunisia Golden League") as Call<ArrayList<Team>?>?
        val tflTeamsCall =
            backendApi.getTeamsByLeague("Tunisia First League") as Call<ArrayList<Team>?>?
        val tglP35TeamsCall =
            backendApi.getTeamsByLeague("Tunisia Golden League P35") as Call<ArrayList<Team>?>?
        var tglTeams: ArrayList<Team>
        var tglP35Teams: ArrayList<Team>
        var tflTeams: ArrayList<Team>
        tglTeamsCall!!.enqueue(object : Callback<ArrayList<Team>?> {
            override fun onResponse(
                call: Call<ArrayList<Team>?>,
                response: Response<ArrayList<Team>?>
            ) {
                if (response.isSuccessful) {
                    tglTeams = response.body()!!
                    tglTeams.sortByDescending { team -> team.points }
                    tgl.setOnClickListener {
                        var intent = Intent(this@MainActivity, Leaderboard::class.java)
                        intent.putExtra("title", "Tunisia Golden League")
                        intent.putExtra("teams", tglTeams)
                        intent.putExtra("matches", tglMatches)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Team>?>, t: Throwable) {
                // displaying an error message in toast
                Toast.makeText(this@MainActivity, "Problème Serveur...", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        tglP35TeamsCall!!.enqueue(object : Callback<ArrayList<Team>?> {
            override fun onResponse(
                call: Call<ArrayList<Team>?>,
                response: Response<ArrayList<Team>?>
            ) {
                if (response.isSuccessful) {
                    tglP35Teams = response.body()!!
                    tglP35Teams.sortByDescending { team -> team.points }
                    tglP35.setOnClickListener {
                        var intent = Intent(this@MainActivity, Leaderboard::class.java)
                        intent.putExtra("title", "Tunisia Golden League +35")
                        intent.putExtra("teams", tglP35Teams)
                        intent.putExtra("matches", tglP35Matches)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Team>?>, t: Throwable) {
                // displaying an error message in toast
                Toast.makeText(this@MainActivity, "Problème Serveur...", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        tflTeamsCall!!.enqueue(object : Callback<ArrayList<Team>?> {
            override fun onResponse(
                call: Call<ArrayList<Team>?>,
                response: Response<ArrayList<Team>?>
            ) {
                if (response.isSuccessful) {
                    tflTeams = response.body()!!
                    tflTeams.sortByDescending { team -> team.points }
                    tfl.setOnClickListener {
                        var intent = Intent(this@MainActivity, Leaderboard::class.java)
                        intent.putExtra("title", "Tunisia First League")
                        intent.putExtra("teams", tflTeams)
                        intent.putExtra("matches", tflMatches)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Team>?>, t: Throwable) {
                // displaying an error message in toast
                Toast.makeText(this@MainActivity, "Problème Serveur...", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}