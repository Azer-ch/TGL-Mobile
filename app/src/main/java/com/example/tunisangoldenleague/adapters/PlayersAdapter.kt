package com.example.tunisangoldenleague.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tunisangoldenleague.R
import com.example.tunisangoldenleague.model.Player

class PlayersAdapter(var players: ArrayList<Player>) :
    RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textView12)
        val goals: TextView = itemView.findViewById(R.id.textView15)
        val cartons_jaunes: TextView = itemView.findViewById(R.id.textView13)
        val cartons_rouges: TextView = itemView.findViewById(R.id.textView14)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayersAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.player_list_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: PlayersAdapter.ViewHolder, position: Int) {
        val player: Player = players.get(position)
        val name = viewHolder.name
        val goals = viewHolder.goals
        val cartons_jaunes: TextView = viewHolder.cartons_jaunes
        val cartons_rouges: TextView = viewHolder.cartons_rouges
        name.setText(player.fullname)
        goals.setText(player.buts.toString())
        cartons_jaunes.setText(player.cartons_jaunes.toString())
        cartons_rouges.setText(player.cartons_rouges.toString())

    }

    override fun getItemCount(): Int {
        return players.size
    }
}
