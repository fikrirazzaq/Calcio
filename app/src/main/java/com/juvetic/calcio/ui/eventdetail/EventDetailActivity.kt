package com.juvetic.calcio.ui.eventdetail

import android.os.Bundle
import com.juvetic.calcio.R
import com.juvetic.calcio.base.BaseActivity
import com.juvetic.calcio.ui.eventdetail.EventDetailFragment.Companion.EVENT_ID

class EventDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        title = ""

        val eventDetailFragment = EventDetailFragment
            .newInstance(intent.getStringExtra(EVENT_ID))

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, eventDetailFragment)
            .commit()
    }
}