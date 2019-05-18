package com.juvetic.calcio.core.presenter.searchteam

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.team.Team

class SearchTeamPresenter(val contract: LeagueContract<Team>, var interactor: SearchTeamInteractor) {

    fun searchTeam(query: String) {
        interactor.searchTeam(query, object : LeagueContract<Team?> {
            override fun onGetDataSuccess(data: Team?) {
                contract.onGetDataSuccess(data)
            }

            override fun onGetDataFailed(message: String) {
                contract.onGetDataFailed(message)
            }

        })
    }
}