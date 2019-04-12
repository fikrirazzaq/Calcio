package com.juvetic.calcio.core.nextevent

import android.content.Context
import com.juvetic.calcio.api.CalcioApi
import com.juvetic.calcio.model.AppResponse
import com.juvetic.calcio.model.event.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class NextEventInteractor(val listener: NextEventDataContract.OnGetNextEventDataListener) :
    NextEventDataContract.Interactor {

    override fun initGetNextEvent(context: Context, id: String) {

        val retrofit: Retrofit = CalcioApi.getClient()
        val response: AppResponse = retrofit.create(AppResponse::class.java)
        val call: Call<Event> = response.getNextEventsLeagueById(id)
        call.enqueue(object : Callback<Event> {
            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                val event: Event? = response.body()
                event?.let { listener.onSuccess("Success lurd " + response.raw(), it) }
            }

            override fun onFailure(call: Call<Event>, t: Throwable) {
                t.message?.let { listener.onFailure(it) }
            }
        })
    }
}