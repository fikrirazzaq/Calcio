package com.juvetic.calcio.ui.favoriteevent


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juvetic.calcio.R
import com.juvetic.calcio.db.database
import com.juvetic.calcio.model.favorite.FavoriteEvent
import com.juvetic.calcio.ui.eventdetail.EventDetailActivity
import com.juvetic.calcio.ui.eventdetail.EventDetailFragment
import com.juvetic.calcio.utils.EventDetailClickListener
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

class FavoriteEventFragment : Fragment(), EventDetailClickListener, AnkoLogger {

    private var favorites: MutableList<FavoriteEvent> = mutableListOf()
    private lateinit var favoriteAdapter: FavoriteEventAdapter
    private lateinit var rcvFavorite: RecyclerView
    private lateinit var toolBar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcvFavorite = view.findViewById(R.id.rcv_favorite_event)
        toolBar = view.findViewById(R.id.toolbar)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolBar.title = getString(R.string.favorite_matches)

        favoriteAdapter = FavoriteEventAdapter(context, favorites, this)
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
            val result = select(FavoriteEvent.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteEvent>())
            favorites.addAll(favorite)
            favoriteAdapter.notifyDataSetChanged()
        }
    }

    override fun onEventDetailClick(eventId: String?) {
        startActivity<EventDetailActivity>(EventDetailFragment.EVENT_ID to eventId)
    }
}
