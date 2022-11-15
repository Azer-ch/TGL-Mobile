package com.example.tunisangoldenleague.adapters

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.tunisangoldenleague.MatchDetails
import com.example.tunisangoldenleague.R
import com.example.tunisangoldenleague.model.Match
import com.squareup.picasso.Picasso

class LiveMatchesAdapter(var matches: ArrayList<Match>) :
    RecyclerView.Adapter<LiveMatchesAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val elapsedTime: TextView = itemView.findViewById(R.id.textView0)
        val homeTeamName : TextView = itemView.findViewById(R.id.textView1)
        val awayTeamName : TextView = itemView.findViewById(R.id.textView3)
        val homeTeamLogo : ImageView = itemView.findViewById(R.id.imageView1)
        val awayTeamLogo : ImageView = itemView.findViewById(R.id.imageView2)
        val score : TextView= itemView.findViewById(R.id.textView2)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LiveMatchesAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.live_match_list_item, parent, false)
        return ViewHolder(contactView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: LiveMatchesAdapter.ViewHolder, position: Int) {
        val match: Match = matches.get(position)
        val homeTeamName = viewHolder.homeTeamName
        val awayTeamName = viewHolder.awayTeamName
        val homeTeamLogo = viewHolder.homeTeamLogo
        val awayTeamLogo = viewHolder.awayTeamLogo
        val score = viewHolder.score
        val elapsedTime = viewHolder.elapsedTime
        homeTeamName.setText(match.homeTeam.name)
        awayTeamName.setText(match.awayTeam.name)
        elapsedTime.setText(match.getElapsedTime())
        score.setText(match.getScore())
        var homeTeamUrl = "http://tgl.westeurope.cloudapp.azure.com${match.homeTeam.image}"
        var awayTeamUrl = "http://tgl.westeurope.cloudapp.azure.com${match.awayTeam.image}"
        Picasso.get().load(homeTeamUrl).into(homeTeamLogo)
        Picasso.get().load(awayTeamUrl).into(awayTeamLogo)
        viewHolder.itemView.setOnClickListener{
            val intent = Intent(viewHolder.itemView.context,MatchDetails::class.java)
            intent.putExtra("title",match.awayTeam.league)
            intent.putExtra("match",match)
            viewHolder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return matches.size
    }
}
