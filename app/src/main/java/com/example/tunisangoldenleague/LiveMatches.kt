package com.example.tunisangoldenleague

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tunisangoldenleague.adapters.LiveMatchesAdapter
import com.example.tunisangoldenleague.adapters.MatchesAdapter
import com.example.tunisangoldenleague.api.BackendAPI
import com.example.tunisangoldenleague.api.RetrofitHelper
import com.example.tunisangoldenleague.model.Match
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LiveMatches : AppCompatActivity() {
    lateinit var liveMatchesList : RecyclerView
    lateinit var backArrow : ImageView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_matches)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        init()
        swipeRefreshLayout.setOnRefreshListener {
            init()
            swipeRefreshLayout.isRefreshing = false
        }
        backArrow = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()
        }
    }
    fun init(){
        var liveMatches: ArrayList<Match>
        val backendApi = RetrofitHelper.getInstance().create(BackendAPI::class.java)
        val liveMatchesCall = backendApi.getLiveMatches() as Call<ArrayList<Match>?>?
        liveMatchesCall!!.enqueue(object : Callback<ArrayList<Match>?> {
            override fun onResponse(
                call: Call<ArrayList<Match>?>,
                response: Response<ArrayList<Match>?>
            ) {
                if (response.isSuccessful) {
                    liveMatches = response.body()!!
                    if (!liveMatches.isEmpty()) {
                        var liveMatchesAdapter = LiveMatchesAdapter(liveMatches)
                        liveMatchesList = findViewById(R.id.recyclerView)
                        liveMatchesList.adapter = liveMatchesAdapter
                        liveMatchesList.layoutManager = LinearLayoutManager(this@LiveMatches)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Match>?>, t: Throwable) {
                Toast.makeText(this@LiveMatches, "Problème de connection...", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}