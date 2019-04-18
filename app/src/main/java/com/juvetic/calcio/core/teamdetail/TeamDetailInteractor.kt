package com.juvetic.calcio.core.teamdetail

import android.content.Context
import com.juvetic.calcio.api.AppResponse
import com.juvetic.calcio.api.CalcioApi
import com.juvetic.calcio.model.team.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit

class TeamDetailInteractor(val listener: TeamDetailDataContract.OnGetTeamDetailDataListener) :
    TeamDetailDataContract.Interactor {

    override fun initGetHomeTeamDetail(context: Context, id: String) {

        val service: Retrofit = CalcioApi.getClient()
        val response: AppResponse = service.create(AppResponse::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val request = response.getTeamDetailById(id)
            withContext(Dispatchers.Main) {
                try {
                    val responseResult = request.await()
                    if (responseResult.isSuccessful) {
                        val leagueDetail: Team? = responseResult.body()
                        leagueDetail?.let { listener.onHomeSuccess("Success lurd home ", it) }
                    } else {
                        listener.onHomeFailure("Error ${responseResult.code()}")
                    }
                } catch (e: HttpException) {
                    listener.onHomeFailure("Error HttpException ${e.message}")
                } catch (e: Throwable) {
                    listener.onHomeFailure("Error else ${e.message}")
                }
            }
        }
    }

    override fun initGetAwayTeamDetail(context: Context, id: String) {

        val service: Retrofit = CalcioApi.getClient()
        val response: AppResponse = service.create(AppResponse::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val request = response.getTeamDetailById(id)
            withContext(Dispatchers.Main) {
                try {
                    val responseResult = request.await()
                    if (responseResult.isSuccessful) {
                        val leagueDetail: Team? = responseResult.body()
                        leagueDetail?.let { listener.onAwaySuccess("Success lurd away ", it) }
                    } else {
                        listener.onAwayFailure("Error ${responseResult.code()}")
                    }
                } catch (e: HttpException) {
                    listener.onAwayFailure("Error HttpException ${e.message}")
                } catch (e: Throwable) {
                    listener.onAwayFailure("Error else ${e.message}")
                }
            }
        }
    }
}