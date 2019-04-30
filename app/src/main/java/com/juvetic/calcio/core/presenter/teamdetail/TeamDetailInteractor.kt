package com.juvetic.calcio.core.presenter.teamdetail

import com.juvetic.calcio.api.Api
import com.juvetic.calcio.api.AppResponse
import com.juvetic.calcio.model.team.Team
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamDetailInteractor {

    fun getHomeTeamDetail(id: String, callback: TeamHomeContract) {
        Api.createService(AppResponse::class.java)
            .getTeamDetailById(id)
            .enqueue(object : Callback<Team> {
                override fun onResponse(call: Call<Team>, response: Response<Team>) {
                    response.let {
                        if (it.isSuccessful) {
                            callback.onGetHomeDataSuccess(it.body())
                        } else {
                            callback.onGetHomeDataFailed(it.message())
                        }
                    }
                }

                override fun onFailure(call: Call<Team>, t: Throwable) {
                    callback.onGetHomeDataFailed(t.message.toString())
                }

            })
    }

    fun getAwayTeamDetail(id: String, callback: TeamAwayContract) {
        Api.createService(AppResponse::class.java)
            .getTeamDetailById(id)
            .enqueue(object : Callback<Team> {
                override fun onResponse(call: Call<Team>, response: Response<Team>) {
                    response.let {
                        if (it.isSuccessful) {
                            callback.onGetAwayDataSuccess(it.body())
                        } else {
                            callback.onGetAwayDataFailed(it.message())
                        }
                    }
                }

                override fun onFailure(call: Call<Team>, t: Throwable) {
                    callback.onGetAwayDataFailed(t.message.toString())
                }
            })
    }
}