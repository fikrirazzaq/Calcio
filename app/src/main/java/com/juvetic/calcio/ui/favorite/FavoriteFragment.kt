package com.juvetic.calcio.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.juvetic.calcio.R
import com.juvetic.calcio.ui.TabAdapter
import com.juvetic.calcio.ui.favoriteevent.FavoriteEventFragment
import com.juvetic.calcio.ui.favoriteteam.FavoriteTeamFragment
import org.jetbrains.anko.AnkoLogger

class FavoriteFragment : Fragment(), AnkoLogger {

    private lateinit var adapter: TabAdapter
    private lateinit var vpFavorite: ViewPager
    private lateinit var tabFavorite: TabLayout
    private lateinit var toolbar: Toolbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vpFavorite = view.findViewById(R.id.vp_favorite)
        tabFavorite = view.findViewById(R.id.tab_favorite)
        toolbar = view.findViewById(R.id.toolbar)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = "Favorite"

        setupTabAdapter()
    }

    private fun setupTabAdapter() {
        activity?.let {
            adapter = TabAdapter((it as AppCompatActivity).supportFragmentManager)
            adapter.addFragment(FavoriteEventFragment(), "Matches")
            adapter.addFragment(FavoriteTeamFragment(), "Teams")
            vpFavorite.adapter = adapter
            tabFavorite.setupWithViewPager(vpFavorite)
        }
    }
}