package com.example.tunisangoldenleague

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
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
        var teams = arrayListOf<Team>(
            Team("Club Africain", "ca", "Tunisia First League", 0),
            Team("Esperance", "est", "Tunisia First League", 0),
            Team("Shell", "shell", "Tunisia Golden League", 0),
            Team("Tunisair", "tunisair", "Tunisia Golden League", 0),
            Team("Club Africain", "ca", "Tunisia Golden League +35", 0),
            Team("Esperance", "est", "Tunisia Golden League +35", 0),
        )
        var matches = arrayListOf<Match>(
            Match(
                teams[0],
                teams[1],
                4,
                0,
                LocalDateTime.of(2022, 8, 10, 11, 30),
                LocalDateTime.of(2022, 8, 10, 13, 0)
            ),
            Match(
                teams[2],
                teams[3],
                2,
                2,
                LocalDateTime.of(2022, 10, 16, 15, 30),
                LocalDateTime.of(2022, 10, 16, 17, 0)
            ),
            Match(
                teams[4],
                teams[5],
                2,
                2,
                LocalDateTime.of(2022, 10, 16, 15, 30),
                LocalDateTime.of(2022, 10, 16, 17, 0)
            )
        )
        var tglMatchesAdapter = MatchesAdapter(matches)
        var tglP35MatchesAdapter = MatchesAdapter(matches)
        var tflMatchesAdapter = MatchesAdapter(matches)
        var liveMatchesAdapter = MatchesAdapter(matches)
        liveMatchesList = findViewById(R.id.recyclerView0)
        tglP35MatchesList = findViewById(R.id.recyclerView)
        tglMatchesList = findViewById(R.id.recyclerView1)
        tflMatchesList = findViewById(R.id.recyclerView2)
        tglMatchesList.adapter = tglMatchesAdapter
        tglP35MatchesList.adapter = tglP35MatchesAdapter
        tflMatchesList.adapter = tflMatchesAdapter
        liveMatchesList.adapter = liveMatchesAdapter
        tglP35MatchesList.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
        )
        tglMatchesList.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
        )
        tflMatchesList.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
        )
        liveMatchesList.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
            )
        )
        live = findViewById(R.id.imageView0)
        tglP35 = findViewById(R.id.textView)
        tgl = findViewById(R.id.textView1)
        tfl = findViewById(R.id.textView2)
        tglP35.setOnClickListener {
            var intent = Intent(this@MainActivity, Leaderboard::class.java)
            intent.putExtra("title", "Tunisia Golden League +35")
            intent.putExtra("teams", teams)
            intent.putExtra("matches", matches)
            startActivity(intent)
        }
        tgl.setOnClickListener {
            var intent = Intent(this@MainActivity, Leaderboard::class.java)
            intent.putExtra("title", "Tunisia Golden League")
            intent.putExtra("teams", teams)
            intent.putExtra("matches", matches)
            startActivity(intent)
        }
        tfl.setOnClickListener {
            var intent = Intent(this@MainActivity, Leaderboard::class.java)
            intent.putExtra("title", "Tunisia First League")
            intent.putExtra("teams", teams)
            intent.putExtra("matches", matches)
            startActivity(intent)
        }
        live.setOnClickListener {
            var intent = Intent(this@MainActivity, LiveMatches::class.java)
            intent.putExtra("matches", matches)
            startActivity(intent)
        }
    }
}