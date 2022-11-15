package com.example.tunisangoldenleague.adapters

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tunisangoldenleague.R
import com.example.tunisangoldenleague.model.Event

class EventsAdapter(var events: ArrayList<Event>) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var time : TextView = itemView.findViewById(R.id.textView14)
        var type : ImageView = itemView.findViewById(R.id.imageView4)
        var playerName : TextView = itemView.findViewById(R.id.textView15)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.event_list_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: EventsAdapter.ViewHolder, position: Int) {
        val event : Event = events.get(position)
        var time = viewHolder.time
        var type = viewHolder.type
        var playerName = viewHolder.playerName
        time.setText("${event.time}'")
        playerName.setText(event.playerName)
        if(event.type=="but"){
            type.setImageResource(R.drawable.goal)
        }else if(event.type=="carton rouge"){
            type.setImageResource(R.drawable.red_card)
        }
        else
            type.setImageResource(R.drawable.yellow_card)
    }

    override fun getItemCount(): Int {
        return events.size
    }
}
