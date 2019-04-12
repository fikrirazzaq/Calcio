package com.juvetic.calcio.model

import com.juvetic.calcio.model.event.Event
import com.juvetic.calcio.model.event.EventSearch
import com.juvetic.calcio.model.league.LeagueDetail
import com.juvetic.calcio.model.team.Team
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AppResponse {

    @GET("lookupleague.php")
    fun getLeagueById(@Query("id") id: String): Call<LeagueDetail>

    @GET("eventspastleague.php")
    fun getPastEventsLeagueById(@Query("id") id: String): Call<Event>

    @GET("eventsnextleague.php")
    fun getNextEventsLeagueById(@Query("id") id: String): Call<Event>

    @GET("lookupevent.php")
    fun getEventDetailById(@Query("id") id: String?): Call<Event>

    @GET("lookupteam.php")
    fun getTeamDetailById(@Query("id") id: String?): Call<Team>

    @GET("searchevents.php")
    fun getEventByQuery(@Query("e") id: String?): Call<EventSearch>
}