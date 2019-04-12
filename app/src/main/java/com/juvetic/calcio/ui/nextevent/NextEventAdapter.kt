package com.juvetic.calcio.ui.nextevent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juvetic.calcio.R
import com.juvetic.calcio.model.event.EventResult
import com.juvetic.calcio.utils.DateUtil.Companion.formatDate
import com.juvetic.calcio.utils.EventDetailClickListener
import kotlinx.android.extensions.LayoutContainer

class NextEventAdapter(
    private val context: Context?, private val items: List<EventResult>?,
    private val listener: EventDetailClickListener
) :
    RecyclerView.Adapter<NextEventAdapter.EventViewHolder>() {

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(items?.get(position))

        holder.itemView.setOnClickListener {
            listener.onEventDetailClick(items?.get(position))
        }
    }

    override fun getItemCount(): Int = items?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item_next_event,
                parent,
                false
            )
        )
    }

    class EventViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val tvDate = itemView.findViewById(R.id.tv_date) as TextView
        private val tvHomeTeam = itemView.findViewById(R.id.tv_home_team_name) as TextView
        private val tvAwayTeam = itemView.findViewById(R.id.tv_away_team_name) as TextView

        fun bind(event: EventResult?) {
            tvDate.text = formatDate(event?.dateEvent)
            tvHomeTeam.text = event?.strHomeTeam
            tvAwayTeam.text = event?.strAwayTeam
        }

    }
}