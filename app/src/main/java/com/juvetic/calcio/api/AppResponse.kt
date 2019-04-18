package com.juvetic.calcio.api

import com.juvetic.calcio.model.event.Event
import com.juvetic.calcio.model.event.EventSearch
import com.juvetic.calcio.model.league.LeagueDetail
import com.juvetic.calcio.model.team.Team
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppResponse {

    @GET("lookupleague.php")
    fun getLeagueById(@Query("id") id: String): Deferred<Response<LeagueDetail>>

    @GET("eventspastleague.php")
    fun getPastEventsLeagueById(@Query("id") id: String): Deferred<Response<Event>>

    @GET("eventsnextleague.php")
    fun getNextEventsLeagueById(@Query("id") id: String): Deferred<Response<Event>>

    @GET("lookupevent.php")
    fun getEventDetailById(@Query("id") id: String?): Deferred<Response<Event>>

    @GET("lookupteam.php")
    fun getTeamDetailById(@Query("id") id: String?): Deferred<Response<Team>>

    @GET("searchevents.php")
    fun getEventByQuery(@Query("e") id: String?): Deferred<Response<EventSearch>>
}