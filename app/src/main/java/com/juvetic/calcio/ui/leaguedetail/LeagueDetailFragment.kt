package com.juvetic.calcio.ui.leaguedetail

import android.os.Bundle
import android.os.Handler
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
import com.juvetic.calcio.core.leaguedetail.LeagueDetailDataContract
import com.juvetic.calcio.core.leaguedetail.LeagueDetailPresenter
import com.juvetic.calcio.model.League
import com.juvetic.calcio.model.league.LeagueDetail
import com.juvetic.calcio.model.league.LeagueDetailResult
import com.juvetic.calcio.ui.TabAdapter
import com.juvetic.calcio.ui.lastevent.LastEventsFragment
import com.juvetic.calcio.ui.leaguedetail.webofficial.WebOfficialFragment
import com.juvetic.calcio.ui.nextevent.NextEventsFragment
import kotlinx.android.synthetic.main.fragment_league_details.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.toast


class LeagueDetailFragment : Fragment(), LeagueDetailDataContract.View, AnkoLogger {

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
        val v = inflater.inflate(R.layout.fragment_league_details, container, false)

        imgBanner = v.findViewById(R.id.img_banner)
        imgBadge = v.findViewById(R.id.img_badge)
        tvCountry = v.findViewById(R.id.tv_country)
        scrollView = v.findViewById(R.id.nested_scroll_view)
        collapsingToolbar = v.findViewById(R.id.collapsing_toolbar)
        toolBar = v.findViewById(R.id.toolbar)
        tvTitle = v.findViewById(R.id.tv_title)
        vpLeague = v.findViewById(R.id.vp_event)
        tabLeague = v.findViewById(R.id.tab_event)

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
        return v
    }

    private fun loadEventDetailPresenter() {
        val leagueDetailPresenter = LeagueDetailPresenter(this)
        context?.let { leagueDetailPresenter.getLeagueById(it, league.id) }
    }

    private fun setupTabAdapter(league: LeagueDetailResult) {
        activity?.let {
            adapter = TabAdapter((it as AppCompatActivity).supportFragmentManager)
            adapter.addFragment(WebOfficialFragment.newInstance(league.strWebsite), "Web")
            adapter.addFragment(LastEventsFragment.newInstance(league.idLeague), "Last Events")
            adapter.addFragment(NextEventsFragment.newInstance(league.idLeague), "Next Events")
            vpLeague.adapter = adapter
            tabLeague.setupWithViewPager(vp_event)
        }
    }

    private fun initTabAdapter() {
        activity?.let {
            adapter = TabAdapter((it as AppCompatActivity).supportFragmentManager)
            adapter.addFragment(WebOfficialFragment(), "Web")
            adapter.addFragment(LastEventsFragment(), "Last Events")
            adapter.addFragment(NextEventsFragment(), "Next Events")
            vpLeague.adapter = adapter
            tabLeague.setupWithViewPager(vp_event)
        }
    }

    override fun onGetDataSuccess(message: String, leagueDetail: LeagueDetail) {
        info(message)
        val league = leagueDetail.leagues[0]

        tvCountry.text = league.strCountry
        context?.let {
            GlideApp.with(it)
                .load(league.strTrophy)
                .into(imgBadge)
        }
        context?.let {
            GlideApp.with(it)
                .load(league.strFanart1)
                .into(imgBanner)
        }

        setupTabAdapter(leagueDetail.leagues[0])
    }

    override fun onGetDataFailure(message: String) {
        debug(message)
        toast("Request timeout, please try again")
        val handler = Handler()
        val changeView = object : Runnable {
            override fun run() {
                activity?.onBackPressed()
                handler.postDelayed(this, 1000L)
            }
        }
        handler.postDelayed(changeView, 1000L)
    }
}