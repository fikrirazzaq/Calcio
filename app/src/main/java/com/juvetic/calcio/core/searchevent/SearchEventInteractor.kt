package com.juvetic.calcio.core.searchevent

import android.content.Context
import com.juvetic.calcio.api.CalcioApi
import com.juvetic.calcio.model.AppResponse
import com.juvetic.calcio.model.event.EventSearch
import org.jetbrains.anko.AnkoLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SearchEventInteractor(val listener: SearchEventDataContract.OnGetSearchEventDataListener) :
    SearchEventDataContract.Interactor, AnkoLogger {

    override fun initSearchEvent(context: Context, query: String?) {

        val retrofit: Retrofit = CalcioApi.getClient()
        val response: AppResponse = retrofit.create(AppResponse::class.java)
        val call: Call<EventSearch> = response.getEventByQuery(query)
        call.enqueue(object : Callback<EventSearch> {
            override fun onResponse(call: Call<EventSearch>, response: Response<EventSearch>) {
                val event: EventSearch? = response.body()
                event?.let {
                    listener.onSuccess(
                        "Success lurd search ${response.raw()}",
                        it
                    )
                }
            }

            override fun onFailure(call: Call<EventSearch>, t: Throwable) {
                t.message?.let { listener.onFailure(it) }
            }
        })
    }
}