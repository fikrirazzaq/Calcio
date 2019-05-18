package com.juvetic.calcio.ui.teamdetail


import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.*
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
import com.juvetic.calcio.core.presenter.teamdetail.TeamDetailInteractor
import com.juvetic.calcio.core.presenter.teamdetail.TeamDetailPresenter
import com.juvetic.calcio.db.database
import com.juvetic.calcio.model.favorite.FavoriteTeam
import com.juvetic.calcio.model.team.Team
import com.juvetic.calcio.model.team.TeamResult
import com.juvetic.calcio.ui.TabAdapter
import com.juvetic.calcio.ui.playerlist.PlayerListFragment
import com.juvetic.calcio.ui.teaminfo.TeamInfoFragment
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.debug
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamDetailFragment : Fragment(), LeagueContract<Team>, AnkoLogger {

    private var isFavorite: Boolean = false
    private lateinit var teamId: String
    private var menuItem: Menu? = null

    private lateinit var team: TeamResult

    private lateinit var adapter: TabAdapter
    private lateinit var scrollView: NestedScrollView
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var toolBar: Toolbar
    private lateinit var vpTeam: ViewPager
    private lateinit var tabTeam: TabLayout
    private lateinit var imgBanner: ImageView
    private lateinit var imgBadge: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvLeague: TextView

    companion object {
        const val TEAM_ID = "team_id"

        fun newInstance(teamId: String): TeamDetailFragment {
            val teamDetailFragment = TeamDetailFragment()
            val bundle = Bundle()
            bundle.putString(TEAM_ID, teamId)
            teamDetailFragment.arguments = bundle
            return teamDetailFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolBar = view.findViewById(R.id.toolbar)
        scrollView = view.findViewById(R.id.nested_scroll_view)
        collapsingToolbar = view.findViewById(R.id.collapsing_toolbar)
        vpTeam = view.findViewById(R.id.vp_team)
        tabTeam = view.findViewById(R.id.tab_team)
        imgBadge = view.findViewById(R.id.img_badge)
        imgBanner = view.findViewById(R.id.img_banner)
        tvTitle = view.findViewById(R.id.tv_title)
        tvLeague = view.findViewById(R.id.tv_league)

        activity?.let {
            (it as AppCompatActivity).setSupportActionBar(toolBar)
        }

        scrollView.isFillViewport = true

        if (arguments != null) {
            teamId = arguments?.getString(TEAM_ID).toString()
        }

        loadTeam()
        checkFavorite()

        toolBar.navigationIcon = context?.getDrawable(R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.team_detail, menu)
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

    override fun onGetDataSuccess(data: Team?) {
        val teamResult = data?.teams

        teamResult?.let {
            this.team = it[0]

            collapsingToolbar.title = it[0].strTeam

            tvTitle.text = it[0].strTeam
            tvLeague.text = it[0].strLeague

            context?.let { it1 ->
                GlideApp.with(it1)
                    .load(it[0].strTeamFanart1)
                    .into(imgBanner)
            }

            context?.let { it2 ->
                GlideApp.with(it2)
                    .load(it[0].strTeamBadge)
                    .into(imgBadge)
            }

            setupTabAdapter(it[0])
        }
    }

    override fun onGetDataFailed(message: String) {
        debug(message)
        toast("Request timeout, please try again")
    }

    private fun setupTabAdapter(team: TeamResult) {
        activity?.let {
            adapter = TabAdapter((it as AppCompatActivity).supportFragmentManager)
            adapter.addFragment(TeamInfoFragment.newInstance(team.idTeam), "Team Info")
            adapter.addFragment(PlayerListFragment.newInstance(team.idTeam), "Squad")
            vpTeam.adapter = adapter
            tabTeam.setupWithViewPager(vpTeam)
        }
    }

    private fun initTabAdapter() {
        activity?.let {
            adapter = TabAdapter((it as AppCompatActivity).supportFragmentManager)
            adapter.addFragment(TeamInfoFragment(), "Team Info")
            adapter.addFragment(PlayerListFragment(), "Squad")
            vpTeam.adapter = adapter
            tabTeam.setupWithViewPager(vpTeam)
        }
    }

    private fun loadTeam() {
        val presenter = TeamDetailPresenter(this, TeamDetailInteractor())
        presenter.getTeamDetail(teamId)
    }

    private fun addToFavorite() {
        try {
            activity?.database?.use {
                insert(
                    FavoriteTeam.TABLE_TEAM,
                    FavoriteTeam.TEAM_ID to team.idTeam,
                    FavoriteTeam.TEAM_NAME to team.strTeam,
                    FavoriteTeam.TEAM_BADGE to team.strTeamBadge
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
                    FavoriteTeam.TABLE_TEAM, "(TEAM_ID = {id})",
                    "id" to teamId
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
                val select = select(FavoriteTeam.TABLE_TEAM)
                    .whereArgs("(TEAM_ID = {id})", "id" to teamId)
                val result = select.parseList(classParser<FavoriteTeam>())
                if (result.isNotEmpty()) isFavorite = true
            }
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }
}
