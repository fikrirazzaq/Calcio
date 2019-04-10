package com.juvetic.calcio.core.leaguedetail

import android.content.Context
import com.juvetic.calcio.model.league.LeagueDetail

interface LeagueDetailDataContract {

    interface View {
        fun onGetDataSuccess(message: String, leagueDetail: LeagueDetail)
        fun onGetDataFailure(message: String)
    }

    interface Presenter {
        fun getLeagueById(context: Context, id: String)
    }

    interface Interactor {
        fun initGetLeagueDetail(context: Context, id: String)
    }

    interface onGetLeagueDetailDataListener {
        fun onSuccess(message: String, leagueDetail: LeagueDetail)
        fun onFailure(message: String)
    }
}