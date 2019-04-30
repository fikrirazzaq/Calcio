package com.juvetic.calcio.core.presenter.leaguedetail

import com.juvetic.calcio.api.Api
import com.juvetic.calcio.api.AppResponse
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.league.LeagueDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeagueDetailInteractor {

    fun getLeagueDetail(id: String, callback: LeagueContract<LeagueDetail?>) {
        Api.createService(AppResponse::class.java)
            .getLeagueById(id)
            .enqueue(object : Callback<LeagueDetail> {
                override fun onResponse(call: Call<LeagueDetail>, response: Response<LeagueDetail>) {
                    response.let {
                        if (it.isSuccessful) {
                            callback.onGetDataSuccess(it.body())
                        } else {
                            callback.onDataError(it.message())
                        }
                    }
                }

                override fun onFailure(call: Call<LeagueDetail>, t: Throwable) {
                    callback.onDataError(t.message.toString())
                }
            })
    }
}