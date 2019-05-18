package com.juvetic.calcio.ui.eventdetail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.juvetic.calcio.GlideApp
import com.juvetic.calcio.R
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.core.presenter.eventdetail.EventDetailInteractor
import com.juvetic.calcio.core.presenter.eventdetail.EventDetailPresenter
import com.juvetic.calcio.core.presenter.eventteamhomeaway.TeamAwayContract
import com.juvetic.calcio.core.presenter.eventteamhomeaway.TeamDetailInteractor
import com.juvetic.calcio.core.presenter.eventteamhomeaway.TeamDetailPresenter
import com.juvetic.calcio.core.presenter.eventteamhomeaway.TeamHomeContract
import com.juvetic.calcio.db.database
import com.juvetic.calcio.model.event.Event
import com.juvetic.calcio.model.event.EventResult
import com.juvetic.calcio.model.favorite.FavoriteEvent
import com.juvetic.calcio.model.team.Team
import com.juvetic.calcio.ui.TabAdapter
import com.juvetic.calcio.utils.DateUtil
import kotlinx.android.synthetic.main.fragment_event_detail.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.toast


class EventDetailFragment : Fragment(),
    LeagueContract<Event>, TeamHomeContract, TeamAwayContract, AnkoLogger {

    private lateinit var event: EventResult
    private lateinit var adapter: TabAdapter
    private lateinit var vpEvent: ViewPager
    private lateinit var tabEvent: TabLayout
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var id: String

    companion object {
        const val EXTRA_EVENT_ITEM = "event_item"
        const val EVENT_ID = "event_id"

        fun newInstance(eventId: String): EventDetailFragment {
            val eventDetailFragment = EventDetailFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_EVENT_ITEM, eventId)
            eventDetailFragment.arguments = bundle
            return eventDetailFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vpEvent = view.findViewById(R.id.vp_event)
        tabEvent = view.findViewById(R.id.tab_event)

        val scrollView: NestedScrollView = view.findViewById(R.id.nested_scroll_view)
        scrollView.isFillViewport = true

        if (arguments != null) {
            id = arguments?.getString(EXTRA_EVENT_ITEM).toString()
        }

        loadEvent()
        checkFavorite()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.event_detail, menu)
        menuItem = menu
        setFavorite()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onGetDataSuccess(data: Event?) {
        info("Success event detail")
        data?.let {
            this.event = it.events[0]
            activity?.title = this.event.strEvent

            tv_date.text = DateUtil.formatDate(this.event.dateEvent)
            tv_league_name.text = this.event.strLeague
            tv_home_score.text = this.event.intHomeScore
            tv_away_score.text = this.event.intAwayScore
            tv_home_name.text = this.event.strHomeTeam
            tv_away_name.text = this.event.strAwayTeam

            // Check Next Events
            if (this.event.intHomeScore != null && this.event.intAwayScore != null) {
                tv_full_time.text = getString(R.string.full_time)
                cl_goal_scorer.visibility = VISIBLE
                view_separator.visibility = VISIBLE
            }

            // Check 0-0 Events
            if (tv_home_score.text == "0" && tv_away_score.text == "0") {
                cl_goal_scorer.visibility = GONE
                view_separator.visibility = GONE
            }

            tv_home_goalscorer.text = setGoalScorerHome(getGoalScorerList(this.event.strHomeGoalDetails))
            tv_away_goalscorer.text = setGoalScorerAway(getGoalScorerList(this.event.strAwayGoalDetails))

            val teamPresenter = TeamDetailPresenter(this, this, TeamDetailInteractor())
            context?.let {
                this.event.idHomeTeam?.let { it1 -> teamPresenter.getHomeTeamDetail(it1) }
                this.event.idAwayTeam?.let { it1 -> teamPresenter.getAwayTeamDetail(it1) }
            }
            setupTabAdapter(it)

        }
    }

    override fun onGetDataFailed(message: String) {
        activity?.runOnUiThread {
            debug("Error $message")
            toast("Request timeout, please try again")
            activity?.onBackPressed()
        }
    }

    override fun onGetHomeDataSuccess(team: Team?) {
        info("Success home team")

        team?.let {
            val t = it.teams[0]

            context?.let { it1 ->
                GlideApp.with(it1)
                    .load(t.strTeamBadge)
                    .into(img_home_logo)
            }
        }
    }

    override fun onGetHomeDataFailed(message: String) {
        debug(message)
        toast("Request timeout, please try again")
    }

    override fun onGetAwayDataSuccess(team: Team?) {
        info("Success away team")

        team?.let {
            val t = it.teams[0]

            context?.let { it1 ->
                GlideApp.with(it1)
                    .load(t.strTeamBadge)
                    .into(img_away_logo)
            }
        }
    }

    override fun onGetAwayDataFailed(message: String) {
        debug(message)
        toast("Request timeout, please try again")
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

    private fun loadEvent() {
        val presenter = EventDetailPresenter(this, EventDetailInteractor())
        presenter.getEventDetail(id)
    }

    private fun addToFavorite() {
        try {
            activity?.database?.use {
                insert(
                    FavoriteEvent.TABLE_FAVORITE,
                    FavoriteEvent.EVENT_ID to event.idEvent,
                    FavoriteEvent.EVENT_DATE to event.dateEvent,
                    FavoriteEvent.EVENT_HOME_TEAM to event.strHomeTeam,
                    FavoriteEvent.EVENT_AWAY_TEAM to event.strAwayTeam,
                    FavoriteEvent.EVENT_HOME_SCORE to event.intHomeScore,
                    FavoriteEvent.EVENT_AWAY_SCORE to event.intAwayScore
                )
            }
            toast(getString(R.string.added_to_favorite))
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun removeFromFavorite() {
        try {
            activity?.database?.use {
                delete(
                    FavoriteEvent.TABLE_FAVORITE, "(EVENT_ID = {id})",
                    "id" to id
                )
            }
            toast(getString(R.string.removed_from_favorite))
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = activity?.let { ContextCompat.getDrawable(it, R.drawable.ic_favorite) }
        else
            menuItem?.getItem(0)?.icon = activity?.let { ContextCompat.getDrawable(it, R.drawable.ic_favorite_border) }
    }

    private fun checkFavorite() {
        try {
            activity?.database?.use {
                val select = select(FavoriteEvent.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})", "id" to id)
                val result = select.parseList(classParser<FavoriteEvent>())
                if (result.isNotEmpty()) isFavorite = true
            }
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }
}