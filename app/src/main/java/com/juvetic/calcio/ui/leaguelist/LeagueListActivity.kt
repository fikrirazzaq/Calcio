package com.juvetic.calcio.ui.leaguelist

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.juvetic.calcio.R
import com.juvetic.calcio.base.BaseActivity
import com.juvetic.calcio.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class LeagueListActivity : BaseActivity() {

    companion object {
        const val LEAGUE_EXTRA = "LEAGUE_EXTRA"
    }

    public lateinit var toolbarMain: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarMain = findViewById(R.id.toolbar)
        setSupportActionBar(toolbarMain)

        navigation.setOnNavigationItemSelectedListener(navigationListener)
        toolbar.title = "League"

        loadFragment(LeagueListFragment.newInstance(this))
    }

    private val navigationListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_league -> {
                    toolbar.title = "League"
                    loadFragment(LeagueListFragment.newInstance(this))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_search_event -> {
                    toolbar.title = "Search Matches"
                    loadFragment(SearchFragment.newInstance(this))
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
