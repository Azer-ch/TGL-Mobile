package com.example.tunisangoldenleague

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tunisangoldenleague.adapters.MatchesAdapter
import com.example.tunisangoldenleague.api.BackendAPI
import com.example.tunisangoldenleague.api.RetrofitHelper
import com.example.tunisangoldenleague.model.League
import com.example.tunisangoldenleague.model.Match
import com.example.tunisangoldenleague.model.Team
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var liveMatchesList: RecyclerView
    lateinit var liveImageView: ImageView
    lateinit var liveTextView: TextView
    lateinit var parentLayout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        parentLayout = findViewById(R.id.parent)
        liveImageView = findViewById(R.id.imageView)
        liveTextView = findViewById(R.id.textView)
        liveMatchesList = findViewById(R.id.recyclerView)
        // Get Leagues
        var leagues = ArrayList<League>()
        val backendApi = RetrofitHelper.getInstance().create(BackendAPI::class.java)
        val leagueCall = backendApi.getLeagues()
        leagueCall!!.enqueue(object : Callback<ArrayList<League>?> {
            override fun onResponse(
                call: Call<ArrayList<League>?>,
                response: Response<ArrayList<League>?>
            ) {
                if (response.isSuccessful) {
                    leagues = response.body()!!
                    var constraintLayouts = ArrayList<ConstraintLayout>()
                    constraintLayouts.add(findViewById(R.id.constraintLayout))
                    for (i in leagues.indices) {
                        runOnUiThread {
                            // creating Constraint layout
                            var layout = ConstraintLayout(this@MainActivity)
                            layout.id = View.generateViewId()
                            layout.background =
                                this@MainActivity.resources.getDrawable(R.drawable.layout_bg)
                            var layoutParam = ConstraintLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                convertToPx(150)
                            )
                            layoutParam.setMargins(
                                convertToPx(10), convertToPx(50), convertToPx(10), convertToPx(50)
                            )
                            layout.layoutParams = layoutParam
                            parentLayout.addView(layout)
                            layoutParam = layout.layoutParams as ConstraintLayout.LayoutParams
                            layoutParam.topToBottom = constraintLayouts[i].id
                            layoutParam.endToEnd = parentLayout.id
                            if (i == leagues.size - 1)
                                layoutParam.bottomToBottom = parentLayout.id
                            layout.requestLayout()
                            // adding title
                            var title = TextView(this@MainActivity)
                            title.id = View.generateViewId()
                            title.setText(leagues[i].name)
                            layoutParam = ConstraintLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            layoutParam.setMargins(
                                convertToPx(10), convertToPx(0), convertToPx(10), convertToPx(10)
                            )
                            title.layoutParams = layoutParam
                            title.setTextColor(this@MainActivity.resources.getColor(R.color.white))
                            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25F)
                            layout.addView(title)
                            layoutParam = title.layoutParams as ConstraintLayout.LayoutParams
                            layoutParam.topToTop = layout.id
                            layoutParam.startToStart = layout.id
                            title.requestLayout()
                            // adding no matchesTextView
                            var noMatchesTextView = TextView(this@MainActivity)
                            noMatchesTextView.id = View.generateViewId()
                            noMatchesTextView.setText("Pas de matches!")
                            layoutParam = ConstraintLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            layoutParam.setMargins(
                                convertToPx(10), convertToPx(10), convertToPx(10), convertToPx(10)
                            )
                            noMatchesTextView.layoutParams = layoutParam
                            noMatchesTextView.setTextColor(this@MainActivity.resources.getColor(R.color.white))
                            noMatchesTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
                            layout.addView(noMatchesTextView)
                            layoutParam =
                                noMatchesTextView.layoutParams as ConstraintLayout.LayoutParams
                            layoutParam.topToBottom = title.id
                            layoutParam.startToStart = layout.id
                            layoutParam.bottomToBottom = layout.id
                            noMatchesTextView.requestLayout()
                            // adding recyclerView
                            var recyclerView = RecyclerView(this@MainActivity)
                            recyclerView.id = View.generateViewId()
                            layoutParam = ConstraintLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            layoutParam.setMargins(0, convertToPx(10), 0, 0)
                            recyclerView.layoutParams = layoutParam
                            layout.addView(recyclerView)
                            layoutParam = recyclerView.layoutParams as ConstraintLayout.LayoutParams
                            layoutParam.startToStart = layout.id
                            layoutParam.topToBottom = title.id
                            layoutParam.bottomToBottom = layout.id
                            recyclerView.requestLayout()
                            constraintLayouts.add(layout)
                            var matches = ArrayList<Match>()
                            var matchesCall = backendApi.getMatchesByLeague(leagues[i].name)
                            matchesCall!!.enqueue(object : Callback<ArrayList<Match>?> {
                                override fun onResponse(
                                    call: Call<ArrayList<Match>?>,
                                    response: Response<ArrayList<Match>?>
                                ) {
                                    if (response.isSuccessful) {
                                        matches = response.body()!!
                                        if (!matches.isEmpty()) {
                                            noMatchesTextView.visibility = View.INVISIBLE
                                            var matchesAdapter = MatchesAdapter(matches)
                                            recyclerView.adapter = matchesAdapter
                                            recyclerView.setLayoutManager(
                                                LinearLayoutManager(
                                                    this@MainActivity,
                                                    LinearLayoutManager.HORIZONTAL, false
                                                )
                                            )
                                        }
                                    }
                                }

                                override fun onFailure(
                                    call: Call<ArrayList<Match>?>,
                                    t: Throwable
                                ) {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Problème Serveur...",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            })
                            var teams: ArrayList<Team>
                            var teamsCall = backendApi.getTeamsByLeague(leagues[i].name)
                            teamsCall!!.enqueue(object : Callback<ArrayList<Team>?> {
                                override fun onResponse(
                                    call: Call<ArrayList<Team>?>,
                                    response: Response<ArrayList<Team>?>
                                ) {
                                    if (response.isSuccessful) {
                                        teams = response.body()!!
                                        teams.sortByDescending { team -> team.points }
                                        title.setOnClickListener {
                                            var intent =
                                                Intent(this@MainActivity, Leaderboard::class.java)
                                            intent.putExtra("title", leagues[i].name)
                                            intent.putExtra("teams", teams)
                                            intent.putExtra("matches", matches)
                                            startActivity(intent)
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<ArrayList<Team>?>, t: Throwable) {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Problème Serveur...",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            })
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<League>?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Problème Serveur...", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        // Get Live Matches
        var liveMatches: ArrayList<Match>
        val liveMatchesCall = backendApi.getLiveMatches() as Call<ArrayList<Match>?>?
        liveMatchesCall!!.enqueue(object : Callback<ArrayList<Match>?> {
            override fun onResponse(
                call: Call<ArrayList<Match>?>,
                response: Response<ArrayList<Match>?>
            ) {
                if (response.isSuccessful) {
                    liveMatches = response.body()!!
                    if (!liveMatches.isEmpty()) {
                        liveTextView.visibility = View.INVISIBLE
                        var liveMatchesAdapter = MatchesAdapter(liveMatches)
                        liveMatchesList.adapter = liveMatchesAdapter
                        liveMatchesList.setLayoutManager(
                            LinearLayoutManager(
                                this@MainActivity,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                        )
                        liveImageView.setOnClickListener {
                            var intent = Intent(this@MainActivity, LiveMatches::class.java)
                            intent.putExtra("matches", liveMatches)
                            startActivity(intent)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Match>?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Problème Serveur...", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun convertToPx(value: Int): Int {
        val r = this.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            r.displayMetrics
        ).toInt()
    }
}