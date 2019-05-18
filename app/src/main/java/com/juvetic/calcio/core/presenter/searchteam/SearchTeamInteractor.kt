package com.juvetic.calcio.core.presenter.searchteam

import com.juvetic.calcio.api.Api
import com.juvetic.calcio.api.AppResponse
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.team.Team
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchTeamInteractor {

    fun searchTeam(query: String, callback: LeagueContract<Team?>) {
        Api.createService(AppResponse::class.java)
            .getTeamByQuery(query)
            .enqueue(object : Callback<Team> {
                override fun onResponse(call: Call<Team>, response: Response<Team>) {
                    response.let {
                        if (it.isSuccessful) {
                            callback.onGetDataSuccess(it.body())
                        } else {
                            callback.onGetDataFailed(it.message())
                        }
                    }
                }

                override fun onFailure(call: Call<Team>, t: Throwable) {
                    callback.onGetDataFailed(t.message.toString())
                }

            })
    }
}