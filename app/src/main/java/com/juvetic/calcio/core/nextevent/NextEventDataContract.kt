package com.juvetic.calcio.core.nextevent

import android.content.Context
import com.juvetic.calcio.model.event.Event

interface NextEventDataContract {

    interface View {
        fun onGetDataSuccess(message: String, event: Event)
        fun onGetDataFailure(message: String)
    }

    interface Presenter {
        fun getNextEventById(context: Context, id: String)
    }

    interface Interactor {
        fun initGetNextEvent(context: Context, id: String)
    }

    interface OnGetNextEventDataListener {
        fun onSuccess(message: String, event: Event)
        fun onFailure(message: String)
    }
}