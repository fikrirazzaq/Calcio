package com.juvetic.calcio.core.teamdetail

import android.content.Context
import com.juvetic.calcio.model.team.Team

class TeamDetailPresenter(val view: TeamDetailDataContract.View) : TeamDetailDataContract.Presenter,
    TeamDetailDataContract.OnGetTeamDetailDataListener {

    private val interactor: TeamDetailInteractor = TeamDetailInteractor(this)

    override fun getHomeTeamDetailById(context: Context, id: String) {
        interactor.initGetHomeTeamDetail(context, id)
    }

    override fun getAwayTeamDetailById(context: Context, id: String) {
        interactor.initGetAwayTeamDetail(context, id)
    }

    override fun onHomeSuccess(message: String, team: Team) {
        view.onGetHomeTeamDataSuccess(message, team)
    }

    override fun onAwaySuccess(message: String, team: Team) {
        view.onGetAwayTeamDataSuccess(message, team)
    }

    override fun onHomeFailure(message: String) {
        view.onGetHomeTeamDataFailure(message)
    }

    override fun onAwayFailure(message: String) {
        view.onGetAwayTeamDataFailure(message)
    }
}