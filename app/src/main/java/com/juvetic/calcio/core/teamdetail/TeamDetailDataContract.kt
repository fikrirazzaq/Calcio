package com.juvetic.calcio.core.teamdetail

import android.content.Context
import com.juvetic.calcio.model.team.Team

interface TeamDetailDataContract {
    interface View {
        fun onGetHomeTeamDataSuccess(message: String, team: Team)
        fun onGetAwayTeamDataSuccess(message: String, team: Team)
        fun onGetHomeTeamDataFailure(message: String)
        fun onGetAwayTeamDataFailure(message: String)
    }

    interface Presenter {
        fun getHomeTeamDetailById(context: Context, id: String)
        fun getAwayTeamDetailById(context: Context, id: String)
    }

    interface Interactor {
        fun initGetHomeTeamDetail(context: Context, id: String)
        fun initGetAwayTeamDetail(context: Context, id: String)
    }

    interface OnGetTeamDetailDataListener {
        fun onHomeSuccess(message: String, team: Team)
        fun onAwaySuccess(message: String, team: Team)
        fun onHomeFailure(message: String)
        fun onAwayFailure(message: String)
    }
}