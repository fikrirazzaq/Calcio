package com.juvetic.calcio.core.presenter.teamdetail

import com.juvetic.calcio.model.team.Team

interface TeamHomeContract {
    fun onGetHomeDataSuccess(team: Team?)
    fun onGetHomeDataFailed(message: String)
}

interface TeamAwayContract {
    fun onGetAwayDataSuccess(team: Team?)
    fun onGetAwayDataFailed(message: String)
}
