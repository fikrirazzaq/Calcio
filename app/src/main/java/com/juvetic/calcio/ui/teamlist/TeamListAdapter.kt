package com.juvetic.calcio.ui.teamlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juvetic.calcio.GlideApp
import com.juvetic.calcio.R
import com.juvetic.calcio.model.team.TeamResult
import com.juvetic.calcio.utils.TeamDetailClickListener
import kotlinx.android.extensions.LayoutContainer

class TeamListAdapter(
    private val context: Context?, private val items: List<TeamResult>?,
    private val listener: TeamDetailClickListener
) : RecyclerView.Adapter<TeamListAdapter.TeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item_team_list, parent, false
            )
        )
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(items?.get(position))

        holder.itemView.setOnClickListener {
            listener.onTeamClick(items?.get(position)?.idTeam)
        }
    }

    class TeamViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val imgLogo = itemView.findViewById(R.id.img_team_logo) as ImageView
        private val tvNam = itemView.findViewById(R.id.tv_team_name) as TextView

        fun bind(team: TeamResult?) {
            tvNam.text = team?.strTeam
            GlideApp.with(containerView.context).load(team?.strTeamBadge).into(imgLogo)
        }
    }
}