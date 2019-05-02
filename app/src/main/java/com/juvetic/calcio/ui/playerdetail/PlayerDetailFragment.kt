package com.juvetic.calcio.ui.playerdetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.juvetic.calcio.GlideApp
import com.juvetic.calcio.R
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.core.presenter.eventteamhomeaway.TeamDetailInteractor
import com.juvetic.calcio.core.presenter.eventteamhomeaway.TeamDetailPresenter
import com.juvetic.calcio.core.presenter.eventteamhomeaway.TeamHomeContract
import com.juvetic.calcio.core.presenter.playerdetail.PlayerDetailInteractor
import com.juvetic.calcio.core.presenter.playerdetail.PlayerDetailPresenter
import com.juvetic.calcio.model.player.PlayerDetail
import com.juvetic.calcio.model.team.Team
import com.juvetic.calcio.utils.DateUtil
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 *
 */
class PlayerDetailFragment : Fragment(), LeagueContract<PlayerDetail>, TeamHomeContract, AnkoLogger {

    private lateinit var playerId: String
    private lateinit var imgCutout: CircleImageView
    private lateinit var imgLogo: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvNationality: TextView
    private lateinit var tvDateOfBirth: TextView
    private lateinit var tvWage: TextView
    private lateinit var tvPosition: TextView
    private lateinit var tvDesc: TextView

    companion object {
        const val PLAYER_ID = "player_id"

        fun newInstance(playerId: String): PlayerDetailFragment {
            val playerDetailFragment = PlayerDetailFragment()
            val bundle = Bundle()
            bundle.putString(PLAYER_ID, playerId)
            playerDetailFragment.arguments = bundle
            return playerDetailFragment
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgCutout = view.findViewById(R.id.img_cutout)
        imgLogo = view.findViewById(R.id.img_team_logo)
        tvName = view.findViewById(R.id.tv_player_name)
        tvDateOfBirth = view.findViewById(R.id.tv_date_of_birth)
        tvNationality = view.findViewById(R.id.tv_nationality)
        tvWage = view.findViewById(R.id.tv_wage)
        tvPosition = view.findViewById(R.id.tv_position)
        tvDesc = view.findViewById(R.id.tv_desc)

        if (arguments != null) {
            playerId = arguments?.getString(PLAYER_ID).toString()
        }

        loadPlayer()
    }

    private fun loadPlayer() {
        val presenter = PlayerDetailPresenter(this, PlayerDetailInteractor())
        presenter.getPlayerDetail(playerId)
    }

    override fun onGetDataSuccess(data: PlayerDetail?) {
        info("Success player detail")

        val player = data?.players?.get(0)

        activity?.title = player?.strPlayer

        context?.let {
            GlideApp.with(it)
                .load(player?.strCutout)
                .into(imgCutout)
        }

        tvName.text = player?.strPlayer
        tvNationality.text = player?.strNationality
        tvPosition.text = player?.strPosition
        tvWage.text = player?.strWage
        tvDateOfBirth.text = DateUtil.formatDateWithYear(player?.dateBorn)
        tvDesc.text = player?.strDescriptionEN

        val presenter2 = TeamDetailPresenter(this, null, TeamDetailInteractor())
        player?.idTeam?.let { presenter2.getHomeTeamDetail(it) }
    }

    override fun onDataError(message: String) {
        debug(message)
        toast("Request timeout, please try again")
    }

    override fun onGetHomeDataSuccess(team: Team?) {
        context?.let {
            GlideApp.with(it)
                .load(team?.teams?.get(0)?.strTeamBadge)
                .into(imgLogo)
        }
    }

    override fun onGetHomeDataFailed(message: String) {
        debug(message)
        toast("Request timeout, please try again")
    }
}
