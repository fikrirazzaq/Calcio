package com.juvetic.calcio.api

import com.juvetic.calcio.model.event.Event
import com.juvetic.calcio.model.event.EventSearch
import com.juvetic.calcio.model.league.LeagueDetail
import com.juvetic.calcio.model.player.Player
import com.juvetic.calcio.model.player.PlayerDetail
import com.juvetic.calcio.model.standings.Standings
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
    fun getEventDetailById(@Query("id") id: String): Call<Event>

    @GET("lookupteam.php")
    fun getTeamDetailById(@Query("id") id: String): Call<Team>

    @GET("searchevents.php")
    fun getEventByQuery(@Query("e") id: String): Call<EventSearch>

    @GET("lookuptable.php")
    fun getStandingsById(@Query("l") id: String): Call<Standings>

    @GET("lookup_all_teams.php")
    fun getTeamListById(@Query("id") id: String): Call<Team>

    @GET("lookup_all_players.php")
    fun getPlayerListById(@Query("id") id: String): Call<Player>

    @GET("lookupplayer.php")
    fun getPlayerDetailById(@Query("id") id: String): Call<PlayerDetail>

    @GET("searchteams.php")
    fun getTeamByQuery(@Query("t") query: String): Call<Team>
}