package com.juvetic.calcio.core.searchevent

import android.content.Context
import com.juvetic.calcio.model.event.Event

interface SearchEventDataContract {

    interface View {
        fun onGetDataSuccess(message: String, event: Event)
        fun onGetDataFailure(message: String)
    }

    interface Presenter {
        fun searchEventByQuery(context: Context, query: String)
    }

    interface Interactor {
        fun initSearchEvent(context: Context, query: String)
    }

    interface onGetSearchEventDataListener {
        fun onSuccess(message: String, event: Event)
        fun onFailure(message: String)
    }
}