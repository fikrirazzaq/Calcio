package com.juvetic.calcio.core.presenter.searchevent

import com.juvetic.calcio.api.Api
import com.juvetic.calcio.api.AppResponse
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.event.EventSearch
import org.jetbrains.anko.AnkoLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchEventInteractor: AnkoLogger{

    fun searchEvent(query: String, callback: LeagueContract<EventSearch?>) {
        Api.createService(AppResponse::class.java)
            .getEventByQuery(query)
            .enqueue(object : Callback<EventSearch> {
                override fun onResponse(call: Call<EventSearch>, response: Response<EventSearch>) {
                    response.let {
                        if (it.isSuccessful) {
                            callback.onGetDataSuccess(it.body())
                        } else {
                            callback.onDataError(it.message())
                        }
                    }
                }

                override fun onFailure(call: Call<EventSearch>, t: Throwable) {
                    callback.onDataError(t.message.toString())
                }

            })
    }
}