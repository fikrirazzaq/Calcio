package com.juvetic.calcio.core.presenter.nextevent

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.event.Event

class NextEventPresenter(val contract: LeagueContract<Event>, var interactor: NextEventInteractor) {

    fun getNextEvent(id: String) {
        interactor.getNextEvent(id, object : LeagueContract<Event?> {
            override fun onGetDataSuccess(data: Event?) {
                contract.onGetDataSuccess(data)
            }

            override fun onGetDataFailed(message: String) {
                contract.onGetDataFailed(message)
            }
        })
    }
}