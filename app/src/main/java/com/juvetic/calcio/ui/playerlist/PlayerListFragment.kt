package com.juvetic.calcio.ui.playerlist


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
import com.juvetic.calcio.core.presenter.playerlist.PlayerListInteractor
import com.juvetic.calcio.core.presenter.playerlist.PlayerListPresenter
import com.juvetic.calcio.model.player.Player
import com.juvetic.calcio.model.player.PlayerResult
import com.juvetic.calcio.ui.playerdetail.PlayerDetailActivity
import com.juvetic.calcio.ui.playerdetail.PlayerDetailFragment
import com.juvetic.calcio.utils.PlayerDetailClickListener
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 *
 */
class PlayerListFragment : Fragment(), AnkoLogger, LeagueContract<Player>, PlayerDetailClickListener {

    private lateinit var rcvPlayerList: RecyclerView
    private lateinit var teamId: String
    private lateinit var presenter: PlayerListPresenter

    companion object {
        const val TEAM_ID = "team_id"

        fun newInstance(teamId: String): PlayerListFragment {
            val playerListFragment = PlayerListFragment()
            val bundle = Bundle()
            bundle.putString(TEAM_ID, teamId)
            playerListFragment.arguments = bundle
            return playerListFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcvPlayerList = view.findViewById(R.id.rcv_player_list)

        if (arguments != null) {
            teamId = arguments?.getString(PlayerListFragment.TEAM_ID).toString()
            presenter = PlayerListPresenter(this, PlayerListInteractor())
            presenter.getPlayerList(teamId)
        }
    }

    override fun onGetDataSuccess(data: Player?) {
        info("Success get team standings")
        data?.let {
            val team: List<PlayerResult> = it.player

            val adapter: PlayerListAdapter? = context?.let { it1 ->
                PlayerListAdapter(it1, team, this)
            }

            rcvPlayerList.adapter = adapter
            val linearLayoutManager = LinearLayoutManager(activity)
            rcvPlayerList.layoutManager = linearLayoutManager
            rcvPlayerList.addItemDecoration(
                DividerItemDecoration(
                    rcvPlayerList.context,
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

    override fun onPlayerClick(playerId: String?) {
        startActivity<PlayerDetailActivity>(PlayerDetailFragment.PLAYER_ID to playerId)
    }
}
