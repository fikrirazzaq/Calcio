package com.juvetic.calcio.core.teamdetail

import android.content.Context
import com.juvetic.calcio.api.CalcioApi
import com.juvetic.calcio.model.AppResponse
import com.juvetic.calcio.model.team.Team
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class TeamDetailInteractor(val listener: TeamDetailDataContract.OnGetTeamDetailDataListener) :
    TeamDetailDataContract.Interactor {

    override fun initGetHomeTeamDetail(context: Context, id: String) {

        val retrofit: Retrofit = CalcioApi.getClient()
        val response: AppResponse = retrofit.create(AppResponse::class.java)
        val call: Call<Team> = response.getTeamDetailById(id)
        call.enqueue(object : Callback<Team> {
            override fun onResponse(call: Call<Team>, response: Response<Team>) {
                val team: Team? = response.body()
                team?.let { listener.onHomeSuccess("Success lurd home" + response.raw(), it) }
            }

            override fun onFailure(call: Call<Team>, t: Throwable) {
                t.message?.let { listener.onHomeFailure(it) }
            }
        })
    }

    override fun initGetAwayTeamDetail(context: Context, id: String) {

        val retrofit: Retrofit = CalcioApi.getClient()
        val response: AppResponse = retrofit.create(AppResponse::class.java)
        val call: Call<Team> = response.getTeamDetailById(id)
        call.enqueue(object : Callback<Team> {
            override fun onResponse(call: Call<Team>, response: Response<Team>) {
                val team: Team? = response.body()
                team?.let { listener.onAwaySuccess("Success lurd away" + response.raw(), it) }
            }

            override fun onFailure(call: Call<Team>, t: Throwable) {
                t.message?.let { listener.onAwayFailure(it) }
            }
        })
    }
}