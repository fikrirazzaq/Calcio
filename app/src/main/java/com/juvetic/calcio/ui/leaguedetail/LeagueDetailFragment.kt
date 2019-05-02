package com.juvetic.calcio.ui.leaguedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.juvetic.calcio.GlideApp
import com.juvetic.calcio.R
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.core.presenter.leaguedetail.LeagueDetailInteractor
import com.juvetic.calcio.core.presenter.leaguedetail.LeagueDetailPresenter
import com.juvetic.calcio.model.League
import com.juvetic.calcio.model.league.LeagueDetail
import com.juvetic.calcio.model.league.LeagueDetailResult
import com.juvetic.calcio.ui.TabAdapter
import com.juvetic.calcio.ui.lastevent.LastEventsFragment
import com.juvetic.calcio.ui.nextevent.NextEventsFragment
import com.juvetic.calcio.ui.standings.StandingsFragment
import com.juvetic.calcio.ui.teamlist.TeamListFragment
import com.juvetic.calcio.ui.webofficial.WebOfficialFragment
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.toast


class LeagueDetailFragment : Fragment(), LeagueContract<LeagueDetail>, AnkoLogger {

    private lateinit var adapter: TabAdapter

    private lateinit var league: League

    private lateinit var tvCountry: TextView
    private lateinit var imgBanner: ImageView
    private lateinit var imgBadge: ImageView
    private lateinit var scrollView: NestedScrollView
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var toolBar: Toolbar
    private lateinit var tvTitle: TextView
    private lateinit var vpLeague: ViewPager
    private lateinit var tabLeague: TabLayout

    companion object {
        const val EXTRA_LEAGUE_ITEM = "league_item"
        const val WEB_URL = "web_url"
        const val LEAGUE_ID = "league_id"

        fun newInstance(league: League): LeagueDetailFragment {
            val leagueDetailFragment = LeagueDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_LEAGUE_ITEM, league)
            leagueDetailFragment.arguments = bundle
            return leagueDetailFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_league_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgBanner = view.findViewById(R.id.img_banner)
        imgBadge = view.findViewById(R.id.img_badge)
        tvCountry = view.findViewById(R.id.tv_country)
        scrollView = view.findViewById(R.id.nested_scroll_view)
        collapsingToolbar = view.findViewById(R.id.collapsing_toolbar)
        toolBar = view.findViewById(R.id.toolbar)
        tvTitle = view.findViewById(R.id.tv_title)
        vpLeague = view.findViewById(R.id.vp_league)
        tabLeague = view.findViewById(R.id.tab_league)

        activity?.let {
            (it as AppCompatActivity).setSupportActionBar(toolBar)
        }

        scrollView.isFillViewport = true

        if (arguments != null) {
            league = arguments?.getParcelable(EXTRA_LEAGUE_ITEM)!!
        }

        loadEventDetailPresenter()

        context?.let {
            GlideApp.with(it)
                .load(league.badge)
                .into(imgBadge)
        }

        toolBar.navigationIcon = context?.getDrawable(R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        collapsingToolbar.title = league.name
        tvTitle.text = league.name

        context?.let { ContextCompat.getColor(it, R.color.primaryTextColor) }?.let {
            collapsingToolbar.setCollapsedTitleTextColor(
                it
            )
        }
        context?.let { ContextCompat.getColor(it, R.color.primaryColor) }?.let {
            collapsingToolbar.setExpandedTitleColor(
                it
            )
        }

        initTabAdapter()
    }

    private fun loadEventDetailPresenter() {
        val leagueDetailPresenter = LeagueDetailPresenter(this, LeagueDetailInteractor())
        leagueDetailPresenter.getLeagueDetail(league.id)
    }

    private fun setupTabAdapter(league: LeagueDetailResult) {
        activity?.let {
            adapter = TabAdapter((it as AppCompatActivity).supportFragmentManager)
            adapter.addFragment(WebOfficialFragment.newInstance(league.strWebsite), getString(R.string.tab_label_web))
            adapter.addFragment(StandingsFragment.newInstance(league.idLeague), getString(R.string.tab_label_table))
            adapter.addFragment(
                LastEventsFragment.newInstance(league.idLeague),
                getString(R.string.tab_label_lastevent)
            )
            adapter.addFragment(
                NextEventsFragment.newInstance(league.idLeague),
                getString(R.string.tab_label_nextevent)
            )
            adapter.addFragment(TeamListFragment.newInstance(league.idLeague), getString(R.string.tab_label_team))
            vpLeague.adapter = adapter
            tabLeague.setupWithViewPager(vpLeague)
        }
    }

    private fun initTabAdapter() {
        activity?.let {
            adapter = TabAdapter((it as AppCompatActivity).supportFragmentManager)
            adapter.addFragment(WebOfficialFragment(), getString(R.string.tab_label_web))
            adapter.addFragment(StandingsFragment(), getString(R.string.tab_label_table))
            adapter.addFragment(LastEventsFragment(), getString(R.string.tab_label_lastevent))
            adapter.addFragment(NextEventsFragment(), getString(R.string.tab_label_nextevent))
            adapter.addFragment(TeamListFragment(), getString(R.string.tab_label_team))
            vpLeague.adapter = adapter
            tabLeague.setupWithViewPager(vpLeague)
        }
    }

    override fun onGetDataSuccess(data: LeagueDetail?) {
        info("Success league detail")
        data?.let {
            val league = it.leagues[0]

            tvCountry.text = league.strCountry
            context?.let { it1 ->
                GlideApp.with(it1)
                    .load(league.strTrophy)
                    .into(imgBadge)
            }
            context?.let { it2 ->
                GlideApp.with(it2)
                    .load(league.strFanart1)
                    .into(imgBanner)
            }

            setupTabAdapter(it.leagues[0])
        }
    }

    override fun onDataError(message: String) {
        activity?.runOnUiThread {
            debug("Error $message")
            toast("Request timeout, please try again")
            activity?.onBackPressed()
        }
    }
}