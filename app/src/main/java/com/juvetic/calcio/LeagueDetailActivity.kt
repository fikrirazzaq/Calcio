package com.juvetic.calcio

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import com.juvetic.calcio.MainActivity.Companion.LEAGUE_EXTRA
import com.juvetic.calcio.model.League
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint

class LeagueDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LeagueDetailActivityUI().setContentView(this)

        val clubBadge = findViewById<ImageView>(R.id.leagueBadgeImageView)
        val clubName = findViewById<TextView>(R.id.leagueNameTextView)
        val clubDesc = findViewById<TextView>(R.id.leagueDescTextView)

        val club = intent.getParcelableExtra<League>(LEAGUE_EXTRA)
        clubName.text = club.name
        clubDesc.text = club.desc
        clubBadge.image.let { GlideApp.with(this).load(club.badge).into(clubBadge) }
    }

    class LeagueDetailActivityUI : AnkoComponent<LeagueDetailActivity> {
        override fun createView(ui: AnkoContext<LeagueDetailActivity>): View = with(ui) {
            constraintLayout {
                padding = dip(16)
                lparams(width = matchParent, height = matchParent)

                imageView {
                    id = R.id.leagueBadgeImageView
                }.lparams(width = dip(110), height = wrapContent) {
                    startToStart = PARENT_ID
                    endToEnd = PARENT_ID
                    topToTop = PARENT_ID
                }

                textView {
                    id = R.id.leagueNameTextView
                    textAppearance = R.style.titleText
                }.lparams(width = matchConstraint, height = wrapContent) {
                    startToStart = PARENT_ID
                    endToEnd = PARENT_ID
                    topToBottom = R.id.leagueBadgeImageView
                    verticalBias = 0f
                    topMargin = dip(16)
                }

                textView {
                    id = R.id.leagueDescTextView
                    textAppearance = R.style.descText
                }.lparams(width = matchConstraint, height = matchConstraint) {
                    startToStart = PARENT_ID
                    endToEnd = PARENT_ID
                    topToBottom = R.id.leagueNameTextView
                    bottomToBottom = PARENT_ID
                    verticalBias = 0f
                    topMargin = dip(8)
                }
            }
        }
    }
}
