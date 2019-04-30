package com.juvetic.calcio.core.presenter.pastevent

import com.juvetic.calcio.api.Api
import com.juvetic.calcio.api.AppResponse
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.event.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PastEventInteractor {

    fun getPastEvent(id: String, callback: LeagueContract<Event?>) {
        Api.createService(AppResponse::class.java)
            .getPastEventsLeagueById(id)
            .enqueue(object: Callback<Event> {
                override fun onResponse(call: Call<Event>, response: Response<Event>) {
                    response.let {
                        if (it.isSuccessful) {
                            callback.onGetDataSuccess(it.body())
                        } else {
                            callback.onDataError(it.message())
                        }
                    }
                }

                override fun onFailure(call: Call<Event>, t: Throwable) {
                    callback.onDataError(t.message.toString())
                }

            })

    }
}