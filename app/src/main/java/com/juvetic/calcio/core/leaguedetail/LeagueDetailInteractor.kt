package com.juvetic.calcio.core.leaguedetail

import android.content.Context
import com.juvetic.calcio.api.CalcioApi
import com.juvetic.calcio.model.AppResponse
import com.juvetic.calcio.model.league.LeagueDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LeagueDetailInteractor
    (val listener: LeagueDetailDataContract.OnGetLeagueDetailDataListener) :
    LeagueDetailDataContract.Interactor {

    override fun initGetLeagueDetail(context: Context, id: String) {

        val retrofit: Retrofit = CalcioApi.getClient()
        val response: AppResponse = retrofit.create(AppResponse::class.java)
        val call: Call<LeagueDetail> = response.getLeagueById(id)
        call.enqueue(object : Callback<LeagueDetail> {
            override fun onResponse(call: Call<LeagueDetail>, response: Response<LeagueDetail>) {
                val leagueDetail: LeagueDetail? = response.body()
                leagueDetail?.let { listener.onSuccess("Success lurd", it) }
            }

            override fun onFailure(call: Call<LeagueDetail>, t: Throwable) {
                t.message?.let { listener.onFailure(it) }
            }
        })
    }
}