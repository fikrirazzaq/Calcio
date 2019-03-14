package com.juvetic.calcio

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juvetic.calcio.adapter.LeagueAdapter
import com.juvetic.calcio.model.League
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity() {

    private var items: MutableList<League> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)

        initData()

        val clubRecyclerView = findViewById<RecyclerView>(R.id.leagueRecyclerView)

        clubRecyclerView.layoutManager = LinearLayoutManager(this)
        clubRecyclerView.adapter = LeagueAdapter(this, items) {
            val intent = Intent(this, LeagueDetailActivity::class.java)
            intent.putExtra(CLUB_EXTRA, it)
            startActivity(intent)
        }
    }

    private fun initData() {
        val clubName = resources.getStringArray(R.array.league_name)
        val clubLogo = resources.obtainTypedArray(R.array.league_logo)
        val clubBadge = resources.obtainTypedArray(R.array.league_badge)
        val clubDesc = resources.getStringArray(R.array.league_desc)

        items.clear()
        for (i in clubName.indices) {
            items.add(
                League(
                    clubName[i],
                    clubLogo.getResourceId(i, 0),
                    clubBadge.getResourceId(i, 0),
                    clubDesc[i]
                )
            )
        }

        clubLogo.recycle()
        clubBadge.recycle()
    }

    // UI List
    class MainActivityUI : AnkoComponent<MainActivity> {
        override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
            constraintLayout {
                recyclerView {
                    id = R.id.leagueRecyclerView
                }.lparams(width = matchConstraint, height = matchConstraint) {
                    startToStart = PARENT_ID
                    endToEnd = PARENT_ID
                    topToTop = PARENT_ID
                    bottomToBottom = PARENT_ID
                }
            }
        }

    }

    companion object {
        const val CLUB_EXTRA = "CLUB_EXTRA"
    }
}
