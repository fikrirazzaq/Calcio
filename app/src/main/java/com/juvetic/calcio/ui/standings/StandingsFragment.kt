package com.juvetic.calcio.ui.standings


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
import com.juvetic.calcio.core.presenter.standings.StandingsInteractor
import com.juvetic.calcio.core.presenter.standings.StandingsPresenter
import com.juvetic.calcio.model.standings.Standings
import com.juvetic.calcio.model.standings.Table
import com.juvetic.calcio.ui.leaguedetail.LeagueDetailFragment
import com.juvetic.calcio.ui.teamdetail.TeamDetailActivity
import com.juvetic.calcio.ui.teamdetail.TeamDetailFragment
import com.juvetic.calcio.utils.TeamDetailClickListener
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 *
 */
class StandingsFragment : Fragment(), AnkoLogger, TeamDetailClickListener, LeagueContract<Standings> {

    private lateinit var leagueId: String
    private lateinit var presenter: StandingsPresenter

    private lateinit var rcvStandings: RecyclerView

    companion object {
        fun newInstance(id: String): StandingsFragment {
            val standingsFragment = StandingsFragment()
            val bundle = Bundle()
            bundle.putString(LeagueDetailFragment.LEAGUE_ID, id)
            standingsFragment.arguments = bundle
            return standingsFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_standings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcvStandings = view.findViewById(R.id.rcv_standings)

        if (arguments != null) {
            leagueId = arguments?.getString(LeagueDetailFragment.LEAGUE_ID).toString()
            presenter = StandingsPresenter(this, StandingsInteractor())
            presenter.getTeams(leagueId)
        }
    }

    override fun onTeamClick(teamId: String?) {
        startActivity<TeamDetailActivity>(TeamDetailFragment.TEAM_ID to teamId)
    }

    override fun onGetDataSuccess(data: Standings?) {
        info("Success get team standings")
        data?.let {
            val team: List<Table> = it.table

            val adapter: StandingsAdapter? = context?.let { it1 ->
                StandingsAdapter(it1, team, this)
            }

            rcvStandings.adapter = adapter
            val linearLayoutManager = LinearLayoutManager(activity)
            rcvStandings.layoutManager = linearLayoutManager
            rcvStandings.addItemDecoration(
                DividerItemDecoration(
                    rcvStandings.context,
                    linearLayoutManager.orientation
                )
            )
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
