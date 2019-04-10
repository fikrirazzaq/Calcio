package com.juvetic.calcio.ui.eventdetail

import android.os.Bundle
import android.view.MenuItem
import com.juvetic.calcio.R
import com.juvetic.calcio.base.BaseActivity
import com.juvetic.calcio.ui.eventdetail.EventDetailFragment.Companion.EVENT_ID

class EventDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        title = ""

        val eventDetailFragment = EventDetailFragment
            .newInstance(intent.getParcelableExtra(EVENT_ID))

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, eventDetailFragment)
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
