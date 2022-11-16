package com.example.tunisangoldenleague.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tunisangoldenleague.MatchDetails
import com.example.tunisangoldenleague.R
import com.example.tunisangoldenleague.TeamDetails
import com.example.tunisangoldenleague.model.Team
import com.squareup.picasso.Picasso

class TeamsAdapter(var teams: ArrayList<Team>) :
    RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo : ImageView = itemView.findViewById(R.id.imageView)
        val name: TextView = itemView.findViewById(R.id.textView10)
        val mj: TextView = itemView.findViewById(R.id.textView11)
        val gagnes: TextView = itemView.findViewById(R.id.textView12)
        val nuls: TextView = itemView.findViewById(R.id.textView13)
        val perdus: TextView = itemView.findViewById(R.id.textView14)
        val buts_marques: TextView = itemView.findViewById(R.id.textView15)
        val buts_encaisses: TextView = itemView.findViewById(R.id.textView16)
        val points: TextView = itemView.findViewById(R.id.textView17)
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
        val logo = viewHolder.logo
        val name = viewHolder.name
        val mj = viewHolder.mj
        val mg = viewHolder.gagnes
        val mn = viewHolder.nuls
        val mp = viewHolder.perdus
        val bp = viewHolder.buts_marques
        val bc = viewHolder.buts_encaisses
        val points = viewHolder.points
        var logoUrl = "http://tgl.westeurope.cloudapp.azure.com${team.image}"
        Picasso.get().load(logoUrl).into(logo)
        name.setText(team.name)
        var totalMatches :Int = team.nulles + team.pertes + team.victoires
        mj.setText(totalMatches.toString())
        mg.setText(team.victoires.toString())
        mn.setText(team.nulles.toString())
        mp.setText(team.pertes.toString())
        bp.setText(team.buts_marque.toString())
        bc.setText(team.buts_encaisse.toString())
        points.setText(team.points.toString())
        viewHolder.itemView.setOnClickListener{
            val intent = Intent(viewHolder.itemView.context, TeamDetails::class.java)
            intent.putExtra("team",team)
            viewHolder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return teams.size
    }
}
