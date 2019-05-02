package com.juvetic.calcio.ui.playerlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juvetic.calcio.GlideApp
import com.juvetic.calcio.R
import com.juvetic.calcio.model.player.PlayerResult
import com.juvetic.calcio.utils.PlayerDetailClickListener
import kotlinx.android.extensions.LayoutContainer

class PlayerListAdapter(
    private val context: Context?,
    private val items: List<PlayerResult>?,
    private val listener: PlayerDetailClickListener
) : RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item_player, parent, false
            )
        )
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(items?.get(position))

        holder.itemView.setOnClickListener {
            listener.onPlayerClick(items?.get(position)?.idPlayer)
        }
    }


    class PlayerViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val imgBadge = itemView.findViewById<ImageView>(R.id.img_badge)
        private val tvPlayerName = itemView.findViewById<TextView>(R.id.tv_player_name)
        private val tvPlayerPos = itemView.findViewById<TextView>(R.id.tv_player_pos)

        fun bind(playerResult: PlayerResult?) {
            GlideApp.with(containerView.context).load(playerResult?.strThumb).into(imgBadge)
            tvPlayerName.text = playerResult?.strPlayer
            tvPlayerPos.text = playerResult?.strPosition
        }
    }


}