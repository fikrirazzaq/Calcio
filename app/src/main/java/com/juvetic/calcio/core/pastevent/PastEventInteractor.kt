package com.juvetic.calcio.core.pastevent

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

class PastEventInteractor
    (val listener: PastEventDataContract.OnGetPastEventDataListener) : PastEventDataContract.Interactor {

    override fun initGetPastEvent(context: Context, id: String) {

        val service: Retrofit = CalcioApi.getClient()
        val response: AppResponse = service.create(AppResponse::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val request = response.getPastEventsLeagueById(id)
            withContext(Dispatchers.Main) {
                try {
                    val responseResult = request.await()
                    if (responseResult.isSuccessful) {
                        val leagueDetail: Event? = responseResult.body()
                        leagueDetail?.let { listener.onSuccess("Success lurd past", it) }
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