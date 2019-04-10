package com.juvetic.calcio.ui.leaguedetail

import android.os.Bundle
import android.view.MenuItem
import com.juvetic.calcio.R
import com.juvetic.calcio.base.BaseActivity
import com.juvetic.calcio.ui.leaguelist.LeagueListActivity.Companion.LEAGUE_EXTRA

class LeagueDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_details)

        val leagueDetailFragment = LeagueDetailFragment.newInstance(intent.getParcelableExtra(LEAGUE_EXTRA))
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, leagueDetailFragment)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
