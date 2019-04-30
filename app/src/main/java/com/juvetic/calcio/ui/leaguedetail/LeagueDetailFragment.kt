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
import com.juvetic.calcio.ui.leaguedetail.webofficial.WebOfficialFragment
import com.juvetic.calcio.ui.nextevent.NextEventsFragment
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
        val v = inflater.inflate(R.layout.fragment_league_details, container, false)

        imgBanner = v.findViewById(R.id.img_banner)
        imgBadge = v.findViewById(R.id.img_badge)
        tvCountry = v.findViewById(R.id.tv_country)
        scrollView = v.findViewById(R.id.nested_scroll_view)
        collapsingToolbar = v.findViewById(R.id.collapsing_toolbar)
        toolBar = v.findViewById(R.id.toolbar)
        tvTitle = v.findViewById(R.id.tv_title)
        vpLeague = v.findViewById(R.id.vp_league)
        tabLeague = v.findViewById(R.id.tab_league)

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
        val leagueDetailPresenter = LeagueDetailPresenter(this, LeagueDetailInteractor())
        leagueDetailPresenter.getLeagueDetail(league.id)
    }

    private fun setupTabAdapter(league: LeagueDetailResult) {
        activity?.let {
            adapter = TabAdapter((it as AppCompatActivity).supportFragmentManager)
            adapter.addFragment(WebOfficialFragment.newInstance(league.strWebsite), "Web")
            adapter.addFragment(LastEventsFragment.newInstance(league.idLeague), "Last Events")
            adapter.addFragment(NextEventsFragment.newInstance(league.idLeague), "Next Events")
            vpLeague.adapter = adapter
            tabLeague.setupWithViewPager(vpLeague)
        }
    }

    private fun initTabAdapter() {
        activity?.let {
            adapter = TabAdapter((it as AppCompatActivity).supportFragmentManager)
            adapter.addFragment(WebOfficialFragment(), "Web")
            adapter.addFragment(LastEventsFragment(), "Last Events")
            adapter.addFragment(NextEventsFragment(), "Next Events")
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