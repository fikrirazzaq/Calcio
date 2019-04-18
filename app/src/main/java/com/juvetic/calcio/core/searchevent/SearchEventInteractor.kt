package com.juvetic.calcio.core.searchevent

import android.content.Context
import com.juvetic.calcio.api.AppResponse
import com.juvetic.calcio.api.CalcioApi
import com.juvetic.calcio.model.event.EventSearch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.AnkoLogger
import retrofit2.HttpException
import retrofit2.Retrofit

class SearchEventInteractor(val listener: SearchEventDataContract.OnGetSearchEventDataListener) :
    SearchEventDataContract.Interactor, AnkoLogger {

    override fun initSearchEvent(context: Context, query: String?) {

        val service: Retrofit = CalcioApi.getClient()
        val response: AppResponse = service.create(AppResponse::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val request = response.getEventByQuery(query)
            withContext(Dispatchers.Main) {
                try {
                    val responseResult = request.await()
                    if (responseResult.isSuccessful) {
                        val leagueDetail: EventSearch? = responseResult.body()
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