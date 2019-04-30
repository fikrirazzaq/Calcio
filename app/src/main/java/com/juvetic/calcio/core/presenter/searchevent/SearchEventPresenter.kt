package com.juvetic.calcio.core.presenter.searchevent

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.event.EventSearch

class SearchEventPresenter (val contract: LeagueContract<EventSearch>, var interactor: SearchEventInteractor) {

    fun searchEvent(query: String) {
        interactor.searchEvent(query, object : LeagueContract<EventSearch?> {
            override fun onGetDataSuccess(data: EventSearch?) {
                contract.onGetDataSuccess(data)
            }

            override fun onDataError(message: String) {
                contract.onDataError(message)
            }

        })
    }
}