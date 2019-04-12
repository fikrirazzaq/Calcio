package com.juvetic.calcio.core.pastevent

import android.content.Context
import com.juvetic.calcio.model.event.Event

interface PastEventDataContract {

    interface View {
        fun onGetDataSuccess(message: String, event: Event)
        fun onGetDataFailure(message: String)
    }

    interface Presenter {
        fun getPastEventById(context: Context, id: String)
    }

    interface Interactor {
        fun initGetPastEvent(context: Context, id: String)
    }

    interface OnGetPastEventDataListener {
        fun onSuccess(message: String, event: Event)
        fun onFailure(message: String)
    }
}