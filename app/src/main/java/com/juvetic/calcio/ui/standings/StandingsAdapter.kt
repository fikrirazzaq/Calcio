package com.juvetic.calcio.ui.standings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juvetic.calcio.R
import com.juvetic.calcio.model.standings.Table
import com.juvetic.calcio.utils.TeamDetailClickListener
import kotlinx.android.extensions.LayoutContainer

class StandingsAdapter(
    private val context: Context?, private val items: List<Table>?,
    private val listener: TeamDetailClickListener
) :
    RecyclerView.Adapter<StandingsAdapter.TeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item_standings, parent, false
            )
        )
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(items?.get(position))

        holder.itemView.setOnClickListener {
            listener.onTeamClick(items?.get(position)?.teamid)
        }
    }

    class TeamViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val tvTeamName = itemView.findViewById(R.id.tv_team) as TextView
        private val tvPlay = itemView.findViewById(R.id.tv_play) as TextView
        private val tvWin = itemView.findViewById(R.id.tv_win) as TextView
        private val tvDraw = itemView.findViewById(R.id.tv_draw) as TextView
        private val tvLose = itemView.findViewById(R.id.tv_lose) as TextView
        private val tvGoalScored = itemView.findViewById(R.id.tv_goalscored) as TextView
        private val tvGoalAgainst = itemView.findViewById(R.id.tv_goalagainst) as TextView
        private val tvGoalDifference = itemView.findViewById(R.id.tv_goaldifference) as TextView
        private val tvPoints = itemView.findViewById(R.id.tv_points) as TextView

        fun bind(table: Table?) {
            tvTeamName.text = table?.name
            tvPlay.text = table?.played.toString()
            tvWin.text = table?.win.toString()
            tvDraw.text = table?.draw.toString()
            tvLose.text = table?.loss.toString()
            tvGoalScored.text = table?.goalsfor.toString()
            tvGoalAgainst.text = table?.goalsagainst.toString()
            tvGoalDifference.text = table?.goalsdifference.toString()
            tvPoints.text = table?.total.toString()
        }

    }
}