package com.juvetic.calcio.core.presenter.teamdetail

import com.juvetic.calcio.api.Api
import com.juvetic.calcio.api.AppResponse
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.team.Team
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamDetailInteractor {

    fun getTeamDetail(id: String, callback: LeagueContract<Team>) {
        Api.createService(AppResponse::class.java)
            .getTeamDetailById(id)
            .enqueue(object : Callback<Team> {
                override fun onResponse(call: Call<Team>, response: Response<Team>) {
                    response.let {
                        if (it.isSuccessful) {
                            callback.onGetDataSuccess(it.body())
                        } else {
                            callback.onDataError(it.message())
                        }
                    }
                }

                override fun onFailure(call: Call<Team>, t: Throwable) {
                    callback.onDataError(t.message.toString())
                }

            })
    }
}