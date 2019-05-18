package com.juvetic.calcio.core.presenter.playerlist

import com.juvetic.calcio.api.Api
import com.juvetic.calcio.api.AppResponse
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.player.Player
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerListInteractor {

    fun getPlayerList(id: String, callback: LeagueContract<Player?>) {
        Api.createService(AppResponse::class.java)
            .getPlayerListById(id)
            .enqueue(object : Callback<Player> {
                override fun onResponse(call: Call<Player>, response: Response<Player>) {
                    response.let {
                        if (it.isSuccessful) {
                            callback.onGetDataSuccess(it.body())
                        } else {
                            callback.onGetDataFailed(it.message())
                        }
                    }
                }

                override fun onFailure(call: Call<Player>, t: Throwable) {
                    callback.onGetDataFailed(t.message.toString())
                }

            })
    }
}