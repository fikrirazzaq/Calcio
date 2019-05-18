package com.juvetic.calcio.core.presenter.eventdetail

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.event.Event

class EventDetailPresenter(val contract: LeagueContract<Event>, var interactor: EventDetailInteractor) {

    fun getEventDetail(id: String) {
        interactor.getEventDetail(id, object: LeagueContract<Event?> {
            override fun onGetDataSuccess(data: Event?) {
                contract.onGetDataSuccess(data)
            }
            override fun onGetDataFailed(message: String) {
                contract.onGetDataFailed(message)
            }
        })
    }
}