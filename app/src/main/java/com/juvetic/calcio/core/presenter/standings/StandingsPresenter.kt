package com.juvetic.calcio.core.presenter.standings

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.standings.Standings

class StandingsPresenter(val contract: LeagueContract<Standings>, var interactor: StandingsInteractor) {

    fun getTeams(leagueId: String) {
        interactor.getLeagueStandings(leagueId, object : LeagueContract<Standings?> {
            override fun onGetDataSuccess(data: Standings?) {
                contract.onGetDataSuccess(data)
            }

            override fun onGetDataFailed(message: String) {
                contract.onGetDataFailed(message)
            }

        })
    }
}