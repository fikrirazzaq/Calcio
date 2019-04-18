package com.juvetic.calcio.model.event

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    val events: List<EventResult>
) : Parcelable

@Parcelize
data class EventSearch(
    val event: List<EventResult>
) : Parcelable

@Parcelize
data class EventResult(
    var dateEvent: String?,
    val idAwayTeam: String?,
    var idEvent: String?,
    val idHomeTeam: String?,
    val idLeague: String?,
    val idSoccerXML: String?,
    var intAwayScore: String?,
    val intAwayShots: String?,
    var intHomeScore: String?,
    val intHomeShots: String?,
    val intRound: String?,
    val intSpectators: String?,
    val strAwayFormation: String?,
    val strAwayGoalDetails: String?,
    val strAwayLineupDefense: String?,
    val strAwayLineupForward: String?,
    val strAwayLineupGoalkeeper: String?,
    val strAwayLineupMidfield: String?,
    val strAwayLineupSubstitutes: String?,
    val strAwayRedCards: String?,
    var strAwayTeam: String?,
    val strAwayYellowCards: String?,
    val strBanner: String?,
    val strCircuit: String?,
    val strCity: String?,
    val strCountry: String?,
    val strDate: String?,
    val strDescriptionEN: String?,
    val strEvent: String?,
    val strFanart: String?,
    val strFilename: String?,
    val strHomeFormation: String?,
    val strHomeGoalDetails: String?,
    val strHomeLineupDefense: String?,
    val strHomeLineupForward: String?,
    val strHomeLineupGoalkeeper: String?,
    val strHomeLineupMidfield: String?,
    val strHomeLineupSubstitutes: String?,
    val strHomeRedCards: String?,
    var strHomeTeam: String?,
    val strHomeYellowCards: String?,
    val strLeague: String?,
    val strLocked: String?,
    val strMap: String?,
    val strPoster: String?,
    val strResult: String?,
    val strSeason: String?,
    val strSport: String?,
    val strTVStation: String?,
    val strThumb: String?,
    val strTime: String?
) : Parcelable