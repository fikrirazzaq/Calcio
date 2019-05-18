package com.juvetic.calcio.core.presenter.standings

import com.juvetic.calcio.api.Api
import com.juvetic.calcio.api.AppResponse
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.standings.Standings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StandingsInteractor {

    fun getLeagueStandings(id: String, callback: LeagueContract<Standings?>) {
        Api.createService(AppResponse::class.java)
            .getStandingsById(id)
            .enqueue(object : Callback<Standings> {
                override fun onResponse(call: Call<Standings>, response: Response<Standings>) {
                    response.let {
                        if (it.isSuccessful) {
                            callback.onGetDataSuccess(it.body())
                        } else {
                            callback.onGetDataFailed(it.message())
                        }
                    }
                }

                override fun onFailure(call: Call<Standings>, t: Throwable) {
                    callback.onGetDataFailed(t.message.toString())
                }

            })
    }
}