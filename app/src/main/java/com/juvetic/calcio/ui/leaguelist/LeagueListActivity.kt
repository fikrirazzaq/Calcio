package com.juvetic.calcio.ui.leaguelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.juvetic.calcio.R
import com.juvetic.calcio.base.BaseActivity
import com.juvetic.calcio.ui.favorite.FavoriteFragment
import com.juvetic.calcio.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class LeagueListActivity : BaseActivity() {

    companion object {
        const val LEAGUE_EXTRA = "LEAGUE_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(navigationListener)

        loadFragment(LeagueListFragment())
    }

    private val navigationListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_league -> {
                    loadFragment(LeagueListFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_search_event -> {
                    loadFragment(SearchFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorite_event -> {
                    loadFragment(FavoriteFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.content, fragment)
            .commit()
    }
}
