package com.juvetic.calcio.model.favorite

class FavoriteEvent(
    val id: Long?, val idEvent: String?, val dateEvent: String?,
    val strHomeTeam: String?, val strAwayTeam: String?,
    val intHomeScore: String?, val intAwayScore: String?
) {
    
    companion object {
        const val TABLE_FAVORITE = "TABLE_FAVORITE"
        const val ID = "ID_"
        const val EVENT_ID = "EVENT_ID"
        const val EVENT_DATE = "EVENT_DATE"
        const val EVENT_HOME_TEAM = "EVENT_HOME_TEAM"
        const val EVENT_AWAY_TEAM = "EVENT_AWAY_TEAM"
        const val EVENT_HOME_SCORE = "EVENT_HOME_SCORE"
        const val EVENT_AWAY_SCORE = "EVENT_AWAY_SCORE"
    }
}