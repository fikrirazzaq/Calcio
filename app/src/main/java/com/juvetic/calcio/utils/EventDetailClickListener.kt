package com.juvetic.calcio.utils

import com.juvetic.calcio.model.event.EventResult

interface EventDetailClickListener {
    fun onEventDetailClick(event: EventResult?)
}