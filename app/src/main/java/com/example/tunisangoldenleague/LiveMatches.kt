package com.example.tunisangoldenleague

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tunisangoldenleague.adapters.LiveMatchesAdapter
import com.example.tunisangoldenleague.model.Match

class LiveMatches : AppCompatActivity() {
    lateinit var liveMatchesList : RecyclerView
    lateinit var backArrow : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_matches)
        var liveMatches = intent.getSerializableExtra("matches") as ArrayList<Match>
        var liveMatchesAdapter = LiveMatchesAdapter(liveMatches)
        liveMatchesList = findViewById(R.id.recyclerView)
        liveMatchesList.adapter = liveMatchesAdapter
        liveMatchesList.layoutManager = LinearLayoutManager(this)
        backArrow = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()
        }
    }
}