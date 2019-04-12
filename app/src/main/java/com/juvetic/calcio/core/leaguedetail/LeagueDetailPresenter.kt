package com.juvetic.calcio.core.leaguedetail

import android.content.Context
import com.juvetic.calcio.model.league.LeagueDetail

class LeagueDetailPresenter(val view: LeagueDetailDataContract.View) : LeagueDetailDataContract.Presenter,
    LeagueDetailDataContract.OnGetLeagueDetailDataListener {

    private val interactor: LeagueDetailInteractor =
        LeagueDetailInteractor(this)

    override fun getLeagueById(context: Context, id: String) {
        interactor.initGetLeagueDetail(context, id)
    }

    override fun onSuccess(message: String, leagueDetail: LeagueDetail) {
        view.onGetDataSuccess(message, leagueDetail)
    }

    override fun onFailure(message: String) {
        view.onGetDataFailure(message)
    }
}