package com.juvetic.calcio.ui.playerdetail

import android.os.Bundle
import android.view.MenuItem
import com.juvetic.calcio.R
import com.juvetic.calcio.base.BaseActivity

class PlayerDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        val playerDetailFragment = PlayerDetailFragment
            .newInstance(intent.getStringExtra(PlayerDetailFragment.PLAYER_ID))

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, playerDetailFragment)
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
