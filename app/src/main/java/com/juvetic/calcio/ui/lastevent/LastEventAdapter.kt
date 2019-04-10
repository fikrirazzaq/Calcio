package com.juvetic.calcio.ui.lastevent

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juvetic.calcio.R
import com.juvetic.calcio.model.event.EventResult
import com.juvetic.calcio.utils.EventDetailClickListener
import kotlinx.android.extensions.LayoutContainer

class LastEventAdapter(
    private val context: Context?, private val items: List<EventResult>?,
    private val listener: EventDetailClickListener
) : RecyclerView.Adapter<LastEventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item_last_event,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(items?.get(position))

        holder.itemView.setOnClickListener {
            listener.onEventDetailClick(items?.get(position))
        }
    }


    class EventViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        val tvDate = itemView.findViewById(R.id.tv_date) as TextView
        val tvHomeTeamName = itemView.findViewById(R.id.tv_home_team_name) as TextView
        val tvAwayTeamName = itemView.findViewById(R.id.tv_away_team_name) as TextView
        val tvHomeTeamScore = itemView.findViewById(R.id.tv_home_team_score) as TextView
        val tvAwayTeamScore = itemView.findViewById(R.id.tv_away_team_score) as TextView

        @SuppressLint("SetTextI18n")
        fun bind(event: EventResult?) {
            tvDate.text = "FT"
            tvHomeTeamName.text = event?.strHomeTeam ?: ""
            tvAwayTeamName.text = event?.strAwayTeam ?: ""
            tvHomeTeamScore.text = event?.intHomeScore ?: ""
            tvAwayTeamScore.text = event?.intAwayScore ?: ""
        }
    }

}