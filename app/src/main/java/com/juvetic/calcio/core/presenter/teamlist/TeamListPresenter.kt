package com.juvetic.calcio.core.presenter.teamlist

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.team.Team

class TeamListPresenter(val contract: LeagueContract<Team>, var interactor: TeamListInteractor) {

    fun getTeams(leagueId: String) {
        interactor.getTeamList(leagueId, object : LeagueContract<Team?> {
            override fun onGetDataSuccess(data: Team?) {
                contract.onGetDataSuccess(data)
            }

            override fun onDataError(message: String) {
                contract.onDataError(message)
            }

        })
    }
}