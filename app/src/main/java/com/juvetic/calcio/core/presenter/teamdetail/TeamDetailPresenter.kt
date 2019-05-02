package com.juvetic.calcio.core.presenter.teamdetail

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.team.Team

class TeamDetailPresenter(val contract: LeagueContract<Team>, var interactor: TeamDetailInteractor) {

    fun getTeamDetail(id: String) {
        interactor.getTeamDetail(id, object : LeagueContract<Team> {
            override fun onGetDataSuccess(data: Team?) {
                contract.onGetDataSuccess(data)
            }

            override fun onDataError(message: String) {
                contract.onDataError(message)
            }
        })
    }
}