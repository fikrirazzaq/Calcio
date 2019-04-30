package com.juvetic.calcio.core.presenter.pastevent

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.event.Event

class PastEventPresenter(val contract: LeagueContract<Event>, var interactor: PastEventInteractor) {

    fun getEventDetail(id: String) {
        interactor.getPastEvent(id, object: LeagueContract<Event?> {
            override fun onGetDataSuccess(data: Event?) {
                contract.onGetDataSuccess(data)
            }
            override fun onDataError(message: String) {
                contract.onDataError(message)
            }
        })
    }
}