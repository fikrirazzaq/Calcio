package com.juvetic.calcio.ui.leaguelist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juvetic.calcio.R
import com.juvetic.calcio.model.League
import com.juvetic.calcio.ui.leaguedetail.LeagueDetailActivity
import com.juvetic.calcio.utils.LeagueDetailClickListener
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.support.v4.startActivity

class LeagueListFragment : Fragment(), LeagueDetailClickListener {

    private val TAG: String = LeagueListFragment::class.java.simpleName

    private var items: MutableList<League> = mutableListOf()

    companion object {
        fun newInstance(context: Context): LeagueListFragment {
            return LeagueListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        val leagueAdapter: LeagueListAdapter? = context?.let {
            LeagueListAdapter(
                it,
                items,
                this
            )
        }
        recycler_view.adapter = leagueAdapter
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