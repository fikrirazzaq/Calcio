package com.juvetic.calcio.core.eventdetail

import android.content.Context
import com.juvetic.calcio.api.AppResponse
import com.juvetic.calcio.api.CalcioApi
import com.juvetic.calcio.model.event.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit

class EventDetailInteractor
    (val listener: EventDetailDataContract.OnGetEventDetailDataListener) : EventDetailDataContract.Interactor {

    override fun iniGetEventDetail(context: Context, id: String?) {

        val service: Retrofit = CalcioApi.getClient()
        val response: AppResponse = service.create(AppResponse::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val request = response.getEventDetailById(id)
            withContext(Dispatchers.Main) {
                try {
                    val responseResult = request.await()
                    if (responseResult.isSuccessful) {
                        val leagueDetail: Event? = responseResult.body()
                        leagueDetail?.let { listener.onSuccess("Success lurd", it) }
                    } else {
                        listener.onFailure("Error ${responseResult.code()}")
                    }
                } catch (e: HttpException) {
                    listener.onFailure("Error HttpException ${e.message}")
                } catch (e: Throwable) {
                    listener.onFailure("Error else ${e.message}")
                }
            }
        }
    }
}