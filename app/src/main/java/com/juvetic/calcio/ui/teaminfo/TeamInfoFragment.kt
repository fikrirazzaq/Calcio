package com.juvetic.calcio.ui.teaminfo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.juvetic.calcio.R
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.core.presenter.teamdetail.TeamDetailInteractor
import com.juvetic.calcio.core.presenter.teamdetail.TeamDetailPresenter
import com.juvetic.calcio.model.team.Team
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamInfoFragment : Fragment(), LeagueContract<Team>, AnkoLogger {

    private lateinit var tvStadium: TextView
    private lateinit var tvCapacity: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvDesc: TextView
    private lateinit var imgStadium: ImageView
    private lateinit var imgLocation: ImageView

    private lateinit var teamId: String

    companion object {
        const val TEAM_ID = "team_id"

        fun newInstance(teamId: String): TeamInfoFragment {
            val teamInfoFragment = TeamInfoFragment()
            val bundle = Bundle()
            bundle.putString(TEAM_ID, teamId)
            teamInfoFragment.arguments = bundle
            return teamInfoFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvStadium = view.findViewById(R.id.tv_stadium)
        tvCapacity = view.findViewById(R.id.tv_capacity)
        tvLocation = view.findViewById(R.id.tv_location)
        tvDesc = view.findViewById(R.id.tv_desc)
        imgLocation = view.findViewById(R.id.img_location)
        imgStadium = view.findViewById(R.id.img_stadium)

        if (arguments != null) {
            teamId = arguments?.getString(TEAM_ID)!!

            val presenter = TeamDetailPresenter(this, TeamDetailInteractor())
            presenter.getTeamDetail(teamId)
        }
    }

    override fun onGetDataSuccess(data: Team?) {
        imgLocation.visibility = View.VISIBLE
        imgStadium.visibility = View.VISIBLE
        data?.let {
            tvStadium.text = it.teams[0].strStadium
            tvCapacity.text = it.teams[0].intStadiumCapacity
            tvLocation.text = it.teams[0].strStadiumLocation
            tvDesc.text = it.teams[0].strDescriptionEN
        }
    }

    override fun onGetDataFailed(message: String) {
        debug(message)
        toast("Request timeout, please try again")
    }
}
