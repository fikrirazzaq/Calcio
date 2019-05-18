package com.juvetic.calcio.core.presenter.playerdetail

import com.juvetic.calcio.api.Api
import com.juvetic.calcio.api.AppResponse
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.player.PlayerDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerDetailInteractor {

    fun getPlayerDetail(id: String, callback: LeagueContract<PlayerDetail?>) {
        Api.createService(AppResponse::class.java)
            .getPlayerDetailById(id)
            .enqueue(object : Callback<PlayerDetail> {
                override fun onResponse(call: Call<PlayerDetail>, response: Response<PlayerDetail>) {
                    response.let {
                        if (it.isSuccessful) {
                            callback.onGetDataSuccess(it.body())
                        } else {
                            callback.onGetDataFailed(it.message())
                        }
                    }
                }

                override fun onFailure(call: Call<PlayerDetail>, t: Throwable) {
                    callback.onGetDataFailed(t.message.toString())
                }

            })
    }
}