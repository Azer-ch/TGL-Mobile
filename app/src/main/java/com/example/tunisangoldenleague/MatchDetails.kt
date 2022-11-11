package com.example.tunisangoldenleague

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.tunisangoldenleague.model.Match
import com.example.tunisangoldenleague.model.Team

class MatchDetails : AppCompatActivity() {
    lateinit var backArrow : ImageView
    lateinit var title : TextView
    lateinit var date : TextView
    lateinit var time : TextView
    lateinit var homeTeam : TextView
    lateinit var awayTeam : TextView
    lateinit var score : TextView
    lateinit var homeLogo : ImageView
    lateinit var awayLogo : ImageView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_details)
        val match  = intent.getSerializableExtra("match") as Match
        backArrow = findViewById(R.id.backArrow)
        title = findViewById(R.id.textView4)
        date = findViewById(R.id.textView5)
        time = findViewById(R.id.textView6)
        homeTeam = findViewById(R.id.textView7)
        homeLogo = findViewById(R.id.homeLogo)
        score = findViewById(R.id.textView8)
        awayTeam = findViewById(R.id.textView9)
        awayLogo = findViewById(R.id.awayLogo)
        title.setText(intent.getStringExtra("title"))
        date.setText(match.getMatchDate())
        time.setText(match.getMatchTime())
        homeTeam.setText(match.homeTeam.name)
        awayTeam.setText(match.awayTeam.name)
        score.setText(match.getScore())
        homeLogo.setImageResource(this.resources.getIdentifier(match.homeTeam.logo,"drawable",this.packageName))
        awayLogo.setImageResource(this.resources.getIdentifier(match.awayTeam.logo,"drawable",this.packageName))
        backArrow.setOnClickListener{
            finish()
        }
    }
}