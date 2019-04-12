package com.juvetic.calcio.core.nextevent

import android.content.Context
import com.juvetic.calcio.model.event.Event

class NextEventPresenter(val view: NextEventDataContract.View) : NextEventDataContract.Presenter,
    NextEventDataContract.OnGetNextEventDataListener {

    private val interactor: NextEventInteractor = NextEventInteractor(this)

    override fun getNextEventById(context: Context, id: String) {
        interactor.initGetNextEvent(context, id)
    }

    override fun onSuccess(message: String, event: Event) {
        view.onGetDataSuccess(message, event)
    }

    override fun onFailure(message: String) {
        view.onGetDataFailure(message)
    }
}