package com.example.tunisangoldenleague

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tunisangoldenleague.adapters.EventsAdapter
import com.example.tunisangoldenleague.api.BackendAPI
import com.example.tunisangoldenleague.api.RetrofitHelper
import com.example.tunisangoldenleague.model.Event
import com.example.tunisangoldenleague.model.Match
import com.example.tunisangoldenleague.model.Team
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchDetails : AppCompatActivity() {
    lateinit var backArrow: ImageView
    lateinit var title: TextView
    lateinit var date: TextView
    lateinit var time: TextView
    lateinit var homeTeam: TextView
    lateinit var awayTeam: TextView
    lateinit var score: TextView
    lateinit var homeLogo: ImageView
    lateinit var awayLogo: ImageView
    lateinit var eventsList : RecyclerView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_details)
        backArrow = findViewById(R.id.backArrow)
        title = findViewById(R.id.textView4)
        date = findViewById(R.id.textView5)
        time = findViewById(R.id.textView6)
        homeTeam = findViewById(R.id.textView7)
        homeLogo = findViewById(R.id.homeLogo)
        score = findViewById(R.id.textView8)
        awayTeam = findViewById(R.id.textView9)
        awayLogo = findViewById(R.id.awayLogo)
        eventsList = findViewById(R.id.eventsList)
        val match = intent.getSerializableExtra("match") as Match
        val backendApi = RetrofitHelper.getInstance().create(BackendAPI::class.java)
        val eventsCall = backendApi.getEventsByMatch(match.id)
        var events = ArrayList<Event>()
        eventsCall!!.enqueue(object : Callback<ArrayList<Event>?> {
            override fun onResponse(
                call: Call<ArrayList<Event>?>,
                response: Response<ArrayList<Event>?>
            ) {
                if (response.isSuccessful) {
                    events = response.body()!!
                    var eventsAdapter = EventsAdapter(events)
                    eventsList.adapter = eventsAdapter
                    eventsList.layoutManager = LinearLayoutManager(this@MatchDetails)
                }
            }
            override fun onFailure(call: Call<ArrayList<Event>?>, t: Throwable) {
                Toast.makeText(
                    this@MatchDetails,
                    "Problème de connection...",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
        title.setText(intent.getStringExtra("title"))
        date.setText(match.getMatchDate())
        time.setText(match.getMatchTime())
        homeTeam.setText(match.homeTeam.name)
        awayTeam.setText(match.awayTeam.name)
        score.setText(match.getScore())
        var homeTeamUrl = "http://tgl.westeurope.cloudapp.azure.com${match.homeTeam.image}"
        var awayTeamUrl = "http://tgl.westeurope.cloudapp.azure.com${match.awayTeam.image}"
        Picasso.get().load(homeTeamUrl).into(homeLogo)
        Picasso.get().load(awayTeamUrl).into(awayLogo)
        backArrow.setOnClickListener {
            finish()
        }
    }
}