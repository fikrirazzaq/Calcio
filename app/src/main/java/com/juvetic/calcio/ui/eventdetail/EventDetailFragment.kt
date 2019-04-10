package com.juvetic.calcio.ui.eventdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.juvetic.calcio.GlideApp
import com.juvetic.calcio.R
import com.juvetic.calcio.core.eventdetail.EventDetailDataContract
import com.juvetic.calcio.core.eventdetail.EventDetailPresenter
import com.juvetic.calcio.core.teamdetail.TeamDetailDataContract
import com.juvetic.calcio.core.teamdetail.TeamDetailPresenter
import com.juvetic.calcio.model.event.Event
import com.juvetic.calcio.model.event.EventResult
import com.juvetic.calcio.model.team.Team
import com.juvetic.calcio.ui.TabAdapter
import com.juvetic.calcio.utils.DateUtil
import kotlinx.android.synthetic.main.fragment_event_detail.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class EventDetailFragment : Fragment(),
    EventDetailDataContract.View, TeamDetailDataContract.View, AnkoLogger {

    private lateinit var event: EventResult
    private lateinit var adapter: TabAdapter
    private lateinit var vpEvent: ViewPager
    private lateinit var tabEvent: TabLayout

    companion object {
        const val EXTRA_EVENT_ITEM = "event_item"
        const val EVENT_ID = "event_id"

        fun newInstance(event: EventResult): EventDetailFragment {
            val eventDetailFragment = EventDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_EVENT_ITEM, event)
            eventDetailFragment.arguments = bundle
            return eventDetailFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_event_detail, container, false)

        vpEvent = v.findViewById(R.id.vp_event)
        tabEvent = v.findViewById(R.id.tab_event)

        val scrollView: NestedScrollView = v.findViewById(R.id.nested_scroll_view)
        scrollView.isFillViewport = true

        if (arguments != null) {
            event = arguments?.getParcelable(EXTRA_EVENT_ITEM)!!

            val eventDetailPresenter = EventDetailPresenter(this)
            context?.let { eventDetailPresenter.getEventById(it, event.idEvent) }
        }

        return v
    }

    override fun onGetDataSuccess(message: String, event: Event) {
        info(message)
        val e = event.events[0]
        activity?.title = e.strEvent

        tv_date.text = DateUtil.formatDate(e.dateEvent)
        tv_league_name.text = e.strLeague
        tv_home_score.text = e.intHomeScore
        tv_away_score.text = e.intAwayScore
        tv_home_name.text = e.strHomeTeam
        tv_away_name.text = e.strAwayTeam

        // Check Next Events
        if (e.intHomeScore != null && e.intAwayScore != null) {
            tv_full_time.text = getString(R.string.full_time)
            cl_goal_scorer.visibility = VISIBLE
            view_separator.visibility = VISIBLE
        }

        // Check 0-0 Events
        if (tv_home_score.text == "0" && tv_away_score.text == "0") {
            cl_goal_scorer.visibility = GONE
            view_separator.visibility = GONE
        }

        tv_home_goalscorer.text = setGoalScorerHome(getGoalScorerList(e.strHomeGoalDetails))
        tv_away_goalscorer.text = setGoalScorerAway(getGoalScorerList(e.strAwayGoalDetails))

        val teamPresenter = TeamDetailPresenter(this)
        context?.let {
            e.idHomeTeam?.let { it1 -> teamPresenter.getHomeTeamDetailById(it, it1) }
            e.idAwayTeam?.let { it1 -> teamPresenter.getAwayTeamDetailById(it, it1) }
        }

        setupTabAdapter(event)
    }

    override fun onGetDataFailure(message: String) {
        error(message)
    }

    override fun onGetHomeTeamDataSuccess(message: String, team: Team) {
        info(message)

        val t = team.teams[0]

        context?.let {
            GlideApp.with(it)
                .load(t.strTeamBadge)
                .into(img_home_logo)
        }
    }

    override fun onGetHomeTeamDataFailure(message: String) {
        error(message)
    }

    override fun onGetAwayTeamDataSuccess(message: String, team: Team) {
        info(message)

        val t = team.teams[0]

        context?.let {
            GlideApp.with(it)
                .load(t.strTeamBadge)
                .into(img_away_logo)
        }
    }

    override fun onGetAwayTeamDataFailure(message: String) {
        error(message)
    }

    private fun setupTabAdapter(event: Event) {
        activity?.let {
            adapter = TabAdapter((it as AppCompatActivity).supportFragmentManager)
            adapter.addFragment(StatsFragment.newInstance(event), getString(R.string.stats))
            adapter.addFragment(LineUpFragment.newInstance(event), getString(R.string.lineup))
            vpEvent.adapter = adapter
            tabEvent.setupWithViewPager(vpEvent)
        }
    }

    private fun setGoalScorerHome(list: List<String>): String {
        var scorer = ""
        info("list size " + list.size)
        for (s in list) {
            info(s)
            if (s.split(":").size == 2) {
                scorer += (getGoalMinutes(s) + " " + getGoalPlayer(s) + "\n")
            }
            info(scorer)
        }
        return scorer
    }

    private fun setGoalScorerAway(list: List<String>): String {
        var scorer = ""
        info("list size " + list.size)
        for (s in list) {
            info(s)
            if (s.split(":").size == 2) {
                scorer += (getGoalPlayer(s) + " " + getGoalMinutes(s) + "\n")
            }
            info(scorer)
        }
        return scorer
    }

    private fun getGoalScorerList(s: String?): List<String> {
        if (s != null) {
            info("AJIG " + s.split(";"))
            return s.split(";")
        }
        return listOf()
    }

    private fun getGoalMinutes(s: String): String {
        val split: List<String> = s.split(":")
        return split[0]
    }

    private fun getGoalPlayer(s: String): String {
        val split: List<String> = s.split(":")
        return split[1]
    }
}