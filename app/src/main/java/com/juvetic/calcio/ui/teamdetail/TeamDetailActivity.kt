package com.juvetic.calcio.ui.teamdetail

import android.os.Bundle
import com.juvetic.calcio.R
import com.juvetic.calcio.base.BaseActivity

class TeamDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        title = ""

        val teamDetailFragment = TeamDetailFragment
            .newInstance(intent.getStringExtra(TeamDetailFragment.TEAM_ID))

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, teamDetailFragment)
            .commit()
    }
}
