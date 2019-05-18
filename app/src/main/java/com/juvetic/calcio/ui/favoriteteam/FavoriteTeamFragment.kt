package com.juvetic.calcio.ui.favoriteteam


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juvetic.calcio.R
import com.juvetic.calcio.db.database
import com.juvetic.calcio.model.favorite.FavoriteTeam
import com.juvetic.calcio.ui.teamdetail.TeamDetailActivity
import com.juvetic.calcio.ui.teamdetail.TeamDetailFragment
import com.juvetic.calcio.utils.TeamDetailClickListener
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity


/**
 * A simple [Fragment] subclass.
 *
 */
class FavoriteTeamFragment : Fragment(), TeamDetailClickListener {

    private var favorites: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var favoriteAdapter: FavoriteTeamAdapter
    private lateinit var rcvFavorite: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcvFavorite = view.findViewById(R.id.rcv_favorite_team)

        favoriteAdapter = FavoriteTeamAdapter(context, favorites, this)
        rcvFavorite.adapter = favoriteAdapter
        val linearLayoutManager = LinearLayoutManager(activity)
        rcvFavorite.layoutManager = linearLayoutManager
        rcvFavorite.addItemDecoration(
            DividerItemDecoration(rcvFavorite.context, linearLayoutManager.orientation)
        )

        showFavorite()
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            val result = select(FavoriteTeam.TABLE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favorites.addAll(favorite)
            favoriteAdapter.notifyDataSetChanged()
        }
    }

    override fun onTeamClick(teamId: String?) {
        startActivity<TeamDetailActivity>(TeamDetailFragment.TEAM_ID to teamId)
    }
}
