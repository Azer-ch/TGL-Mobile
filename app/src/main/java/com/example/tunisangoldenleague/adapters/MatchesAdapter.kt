package com.example.tunisangoldenleague.adapters

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tunisangoldenleague.MatchDetails
import com.example.tunisangoldenleague.R
import com.example.tunisangoldenleague.enum.DivisionEnum
import com.example.tunisangoldenleague.model.Match

class MatchesAdapter(var matches: ArrayList<Match>) :
    RecyclerView.Adapter<MatchesAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val homeTeamName : TextView = itemView.findViewById(R.id.textView1)
        val awayTeamName : TextView = itemView.findViewById(R.id.textView3)
        val homeTeamLogo : ImageView = itemView.findViewById(R.id.imageView1)
        val awayTeamLogo : ImageView = itemView.findViewById(R.id.imageView2)
        val score : TextView= itemView.findViewById(R.id.textView2)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchesAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.match_list_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: MatchesAdapter.ViewHolder, position: Int) {
        val match: Match = matches.get(position)
        val homeTeamName = viewHolder.homeTeamName
        val awayTeamName = viewHolder.awayTeamName
        val homeTeamLogo = viewHolder.homeTeamLogo
        val awayTeamLogo = viewHolder.awayTeamLogo
        val score = viewHolder.score
        homeTeamName.setText(match.homeTeam.name)
        awayTeamName.setText(match.awayTeam.name)
        score.setText(match.getScore())
        var homeID = viewHolder.itemView.context.resources.getIdentifier(match.homeTeam.logo,"drawable",viewHolder.itemView.context.packageName)
        var awayID = viewHolder.itemView.context.resources.getIdentifier(match.awayTeam.logo,"drawable",viewHolder.itemView.context.packageName)
        homeTeamLogo.setImageResource(homeID)
        awayTeamLogo.setImageResource(awayID)
        viewHolder.itemView.setOnClickListener{
            val intent = Intent(viewHolder.itemView.context,MatchDetails::class.java)
            if(match.awayTeam.division == DivisionEnum.JUNIOR)
                intent.putExtra("championnant","Championnant Junior")
            else
                intent.putExtra("championnant","championnant Senior")
            intent.putExtra("match",match)
            viewHolder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return matches.size
    }
}