package com.juvetic.calcio.core.eventdetail

import android.content.Context
import com.juvetic.calcio.model.event.Event

interface EventDetailDataContract {

    interface View {
        fun onGetDataSuccess(message: String, event: Event)
        fun onGetDataFailure(message: String)
    }

    interface Presenter {
        fun getEventById(context: Context, id: String?)
    }

    interface Interactor {
        fun iniGetEventDetail(context: Context, id: String?)
    }

    interface onGetEventDetailDataListener {
        fun onSuccess(message: String, event: Event)
        fun onFailure(message: String)
    }
}