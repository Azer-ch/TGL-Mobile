package com.example.tunisangoldenleague.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tunisangoldenleague.R
import com.example.tunisangoldenleague.dto.PlayerDto
import com.example.tunisangoldenleague.model.Player
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso

class PlayerDtoDetailsAdapter(var players: ArrayList<PlayerDto>) :
    RecyclerView.Adapter<PlayerDtoDetailsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamName : TextView = itemView.findViewById(R.id.textView1)
        val name: TextView = itemView.findViewById(R.id.textView2)
        val goals: TextView = itemView.findViewById(R.id.textView3)
        val teamImage : CircularImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayerDtoDetailsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.player_dto_list_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: PlayerDtoDetailsAdapter.ViewHolder, position: Int) {
        val player: PlayerDto = players.get(position)
        val name = viewHolder.name
        val goals = viewHolder.goals
        val teamName = viewHolder.teamName
        val teamImage = viewHolder.teamImage
        name.setText(player.fullname)
        goals.setText(player.buts.toString())
        teamName.setText(player.team_name)
        var teamImageUrl = "http://tgl2.westeurope.cloudapp.azure.com${player.team_image}"
        Picasso.get().load(teamImageUrl).into(teamImage)
    }

    override fun getItemCount(): Int {
        return players.size
    }
}
