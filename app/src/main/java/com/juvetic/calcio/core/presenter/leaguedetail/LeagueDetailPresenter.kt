package com.juvetic.calcio.core.presenter.leaguedetail

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.league.LeagueDetail

class LeagueDetailPresenter(val contract: LeagueContract<LeagueDetail>, var interactor: LeagueDetailInteractor) {

    fun getLeagueDetail(id: String) {
        interactor.getLeagueDetail(id, object :LeagueContract<LeagueDetail?> {
            override fun onGetDataSuccess(data: LeagueDetail?) {
                contract.onGetDataSuccess(data)
            }

            override fun onDataError(message: String) {
                contract.onDataError(message)
            }

        })
    }
}