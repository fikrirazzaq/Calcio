package com.juvetic.calcio.core.searchevent

import android.content.Context
import com.juvetic.calcio.model.event.Event

class SearchEventPresenter (val view: SearchEventDataContract.View) : SearchEventDataContract.Presenter,
    SearchEventDataContract.onGetSearchEventDataListener {

    private val interactor: SearchEventInteractor = SearchEventInteractor(this)

    override fun searchEventByQuery(context: Context, query: String) {
        interactor.initSearchEvent(context, query)
    }

    override fun onSuccess(message: String, event: Event) {
        view.onGetDataSuccess(message, event)
    }

    override fun onFailure(message: String) {
        view.onGetDataFailure(message)
    }
}