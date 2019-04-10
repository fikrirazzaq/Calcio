package com.juvetic.calcio.ui.leaguelist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.juvetic.calcio.GlideApp
import com.juvetic.calcio.R
import com.juvetic.calcio.model.League
import com.juvetic.calcio.utils.LeagueDetailClickListener
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint

class LeagueListAdapter(
    private val context: Context, private val items: List<League>,
    private val listener: LeagueDetailClickListener
) : RecyclerView.Adapter<LeagueListAdapter.LeagueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder =
        LeagueViewHolder(
            LeagueItemUI().createView(
                AnkoContext.create(context)
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bind(items[position])

        ViewCompat.setTransitionName(holder.leagueLogo, items[position].name)

        holder.itemView.setOnClickListener {
            listener.onLeagueDetailClick(items[position])
        }
    }

    class LeagueViewHolder(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        val leagueLogo: ImageView = itemView.findViewById(R.id.leagueLogoImageView) as ImageView
        val leagueName: TextView = itemView.findViewById(R.id.leagueNameTextView) as TextView

        fun bind(league: League) {

            leagueLogo.let { GlideApp.with(containerView.context).load(league.badge).into(leagueLogo) }
            leagueName.text = league.name
        }
    }

    // UI League Item
    class LeagueItemUI : AnkoComponent<Context> {
        override fun createView(ui: AnkoContext<Context>): View = with(ui) {
            constraintLayout {
                padding = dip(10)
                lparams(width = matchParent, height = wrapContent)

                imageView {
                    id = R.id.leagueLogoImageView
                }.lparams(width = wrapContent, height = dip(50)) {
                    startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                }

                textView {
                    id = R.id.leagueNameTextView
                }.lparams(width = matchConstraint, height = wrapContent) {
                    startToEnd = R.id.leagueLogoImageView
                    endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                    margin = dip(10)
                }
            }
        }
    }

}