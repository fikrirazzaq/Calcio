package com.juvetic.calcio.ui.leaguelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.juvetic.calcio.R
import com.juvetic.calcio.model.League
import com.juvetic.calcio.ui.leaguedetail.LeagueDetailActivity
import com.juvetic.calcio.utils.LeagueDetailClickListener
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.support.v4.startActivity

class LeagueListFragment : Fragment(), LeagueDetailClickListener {

    private var items: MutableList<League> = mutableListOf()
    private lateinit var toolBar: Toolbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_main, container, false)

        toolBar = v.findViewById(R.id.toolbar)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.league)

        initData()
        val leagueAdapter: LeagueListAdapter? = context?.let {
            LeagueListAdapter(
                it,
                items,
                this
            )
        }
        rcv_league.adapter = leagueAdapter
    }

    override fun onLeagueDetailClick(league: League) {
        startActivity<LeagueDetailActivity>(LeagueListActivity.LEAGUE_EXTRA to league)
    }

    private fun initData() {
        val leagueId = resources.getStringArray(R.array.league_id)
        val leagueName = resources.getStringArray(R.array.league_name)
        val leagueLogo = resources.obtainTypedArray(R.array.league_logo)
        val leagueBadge = resources.obtainTypedArray(R.array.league_badge)
        val leagueDesc = resources.getStringArray(R.array.league_desc)

        items.clear()
        for (i in leagueName.indices) {
            items.add(
                League(
                    leagueId[i],
                    leagueName[i],
                    leagueLogo.getResourceId(i, 0),
                    leagueBadge.getResourceId(i, 0),
                    leagueDesc[i]
                )
            )
        }

        leagueLogo.recycle()
        leagueBadge.recycle()
    }
}