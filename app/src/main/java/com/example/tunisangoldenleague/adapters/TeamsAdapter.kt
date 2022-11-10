package com.example.tunisangoldenleague.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tunisangoldenleague.R
import com.example.tunisangoldenleague.model.Team

class TeamsAdapter(var teams: ArrayList<Team>) :
    RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.textView12)
        val points : TextView = itemView.findViewById(R.id.textView13)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeamsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.leaderboard_list_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: TeamsAdapter.ViewHolder, position: Int) {
        val team: Team = teams.get(position)
        val name = viewHolder.name
        val points = viewHolder.points
        name.setText(team.name)
        points.setText(team.points.toString())

    }

    override fun getItemCount(): Int {
        return teams.size
    }
}
