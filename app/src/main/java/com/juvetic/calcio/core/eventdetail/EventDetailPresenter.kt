package com.juvetic.calcio.core.eventdetail

import android.content.Context
import com.juvetic.calcio.model.event.Event

class EventDetailPresenter(val view: EventDetailDataContract.View)
    : EventDetailDataContract.Presenter, EventDetailDataContract.onGetEventDetailDataListener {

    private val interactor: EventDetailInteractor = EventDetailInteractor(this)

    override fun getEventById(context: Context, id: String?) {
        interactor.iniGetEventDetail(context, id)
    }

    override fun onSuccess(message: String, event: Event) {
        view.onGetDataSuccess(message, event)
    }

    override fun onFailure(message: String) {
        view.onGetDataFailure(message)
    }
}