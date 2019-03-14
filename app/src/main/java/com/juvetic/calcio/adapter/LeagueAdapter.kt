package com.juvetic.calcio.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.juvetic.calcio.GlideApp
import com.juvetic.calcio.R
import com.juvetic.calcio.model.League
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint

class LeagueAdapter(
    private val context: Context, private val items: List<League>,
    private val listener: (League) -> Unit
) : RecyclerView.Adapter<LeagueAdapter.ClubViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueAdapter.ClubViewHolder =
        ClubViewHolder(ClubItemListUI().createView(AnkoContext.create(context)))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: LeagueAdapter.ClubViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    class ClubViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val leagueLogo: ImageView = itemView.findViewById(R.id.leagueLogoImageView) as ImageView
        private val leagueName: TextView = itemView.findViewById(R.id.leagueNameTextView) as TextView

        fun bind(club: League, listener: (League) -> Unit) {

            leagueLogo.let { GlideApp.with(containerView.context).load(club.logo).into(leagueLogo) }
            leagueName.text = club.name
            itemView.setOnClickListener { listener(club) }
        }
    }

    // UI League Item
    class ClubItemListUI : AnkoComponent<Context> {
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
                    textAppearance = R.style.titleText
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