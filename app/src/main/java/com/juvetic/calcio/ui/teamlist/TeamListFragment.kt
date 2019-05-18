package com.juvetic.calcio.ui.teamlist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juvetic.calcio.R
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.core.presenter.teamlist.TeamListInteractor
import com.juvetic.calcio.core.presenter.teamlist.TeamListPresenter
import com.juvetic.calcio.model.team.Team
import com.juvetic.calcio.model.team.TeamResult
import com.juvetic.calcio.ui.leaguedetail.LeagueDetailFragment
import com.juvetic.calcio.ui.teamdetail.TeamDetailActivity
import com.juvetic.calcio.ui.teamdetail.TeamDetailFragment
import com.juvetic.calcio.utils.TeamDetailClickListener
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class TeamListFragment : Fragment(), AnkoLogger, TeamDetailClickListener, LeagueContract<Team> {

    private lateinit var leagueId: String
    private lateinit var presenter: TeamListPresenter

    private lateinit var rcvTeamList: RecyclerView

    companion object {
        fun newInstance(id: String): TeamListFragment {
            val teamListFragment = TeamListFragment()
            val bundle = Bundle()
            bundle.putString(LeagueDetailFragment.LEAGUE_ID, id)
            teamListFragment.arguments = bundle
            return teamListFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcvTeamList = view.findViewById(R.id.rcv_team_list)

        if (arguments != null) {
            leagueId = arguments?.getString(LeagueDetailFragment.LEAGUE_ID)!!
            presenter = TeamListPresenter(this, TeamListInteractor())
            presenter.getTeams(leagueId)
        }
    }

    override fun onTeamClick(teamId: String?) {
        startActivity<TeamDetailActivity>(TeamDetailFragment.TEAM_ID to teamId)
    }

    override fun onGetDataSuccess(data: Team?) {
        info("Success get team standings")
        data?.let {
            val team: List<TeamResult> = it.teams

            val adapter: TeamListAdapter? = context?.let { it1 ->
                TeamListAdapter(it1, team, this)
            }

            rcvTeamList.adapter = adapter
            val linearLayoutManager = LinearLayoutManager(activity)
            rcvTeamList.layoutManager = linearLayoutManager
            rcvTeamList.addItemDecoration(
                DividerItemDecoration(
                    rcvTeamList.context,
                    linearLayoutManager.orientation
                )
            )
        }
    }

    override fun onGetDataFailed(message: String) {
        activity?.runOnUiThread {
            debug("Error $message")
            toast("Request timeout, please try again")
            activity?.onBackPressed()
        }
    }
}
