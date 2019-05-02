package com.juvetic.calcio.core.presenter.eventteamhomeaway

import com.juvetic.calcio.model.team.Team

class TeamDetailPresenter(
    val homeContract: TeamHomeContract,
    val awayContract: TeamAwayContract?,
    var interactor: TeamDetailInteractor
) {

    fun getHomeTeamDetail(id: String) {
        interactor.getHomeTeamDetail(id, object : TeamHomeContract {
            override fun onGetHomeDataSuccess(team: Team?) {
                homeContract.onGetHomeDataSuccess(team)
            }

            override fun onGetHomeDataFailed(message: String) {
                homeContract.onGetHomeDataFailed(message)
            }
        })
    }

    fun getAwayTeamDetail(id: String) {
        interactor.getAwayTeamDetail(id, object : TeamAwayContract {
            override fun onGetAwayDataSuccess(team: Team?) {
                awayContract?.onGetAwayDataSuccess(team)
            }

            override fun onGetAwayDataFailed(message: String) {
                awayContract?.onGetAwayDataFailed(message)
            }
        })
    }
}