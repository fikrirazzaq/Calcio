package com.juvetic.calcio.core.pastevent

import android.content.Context
import com.juvetic.calcio.model.event.Event

class PastEventPresenter(val view: PastEventDataContract.View) : PastEventDataContract.Presenter,
    PastEventDataContract.onGetPastEventDataListener {

    private val interactor: PastEventInteractor = PastEventInteractor(this)

    override fun getPastEventById(context: Context, id: String) {
        interactor.initGetPastEvent(context, id)
    }

    override fun onSuccess(message: String, event: Event) {
        view.onGetDataSuccess(message, event)
    }

    override fun onFailure(message: String) {
        view.onGetDataFailure(message)
    }
}