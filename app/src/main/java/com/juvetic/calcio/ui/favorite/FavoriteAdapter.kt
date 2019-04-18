package com.juvetic.calcio.ui.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juvetic.calcio.R
import com.juvetic.calcio.model.Favorite
import com.juvetic.calcio.utils.DateUtil
import com.juvetic.calcio.utils.EventDetailClickListener
import kotlinx.android.extensions.LayoutContainer

class FavoriteAdapter(private val context: Context?, private val items: List<Favorite>?,
                      private val listener: EventDetailClickListener
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item_last_event,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(items?.get(position))

        holder.itemView.setOnClickListener {
            listener.onEventDetailClick(items?.get(position)?.idEvent)
        }
    }


    class FavoriteViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private val tvDate = itemView.findViewById(R.id.tv_date) as TextView
        private val tvHomeTeamName = itemView.findViewById(R.id.tv_home_team_name) as TextView
        private val tvAwayTeamName = itemView.findViewById(R.id.tv_away_team_name) as TextView
        private val tvHomeTeamScore = itemView.findViewById(R.id.tv_home_team_score) as TextView
        private val tvAwayTeamScore = itemView.findViewById(R.id.tv_away_team_score) as TextView

        @SuppressLint("SetTextI18n")
        fun bind(event: Favorite?) {
            tvDate.text = if (event?.dateEvent == "") "" else DateUtil.formatDate(event?.dateEvent)
            tvHomeTeamName.text = event?.strHomeTeam ?: ""
            tvAwayTeamName.text = event?.strAwayTeam ?: ""
            tvHomeTeamScore.text = event?.intHomeScore ?: ""
            tvAwayTeamScore.text = event?.intAwayScore ?: ""
        }
    }

}