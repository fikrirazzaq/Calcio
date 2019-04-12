package com.juvetic.calcio.core.searchevent

import android.content.Context
import com.juvetic.calcio.model.event.EventSearch

interface SearchEventDataContract {

    interface View {
        fun onGetDataSuccess(message: String, event: EventSearch)
        fun onGetDataFailure(message: String)
    }

    interface Presenter {
        fun searchEventByQuery(context: Context, query: String?)
    }

    interface Interactor {
        fun initSearchEvent(context: Context, query: String?)
    }

    interface OnGetSearchEventDataListener {
        fun onSuccess(message: String, event: EventSearch)
        fun onFailure(message: String)
    }
}