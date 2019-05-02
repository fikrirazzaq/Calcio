package com.juvetic.calcio.model.favorite

class FavoriteTeam(
    val id: Long?, val idTeam: String?, val strTeam: String?, val strTeamBadge: String?
) {

    companion object {
        const val TABLE_TEAM = "TABLE_TEAM"
        const val ID = "ID_"
        const val TEAM_ID = "TEAM_ID"
        const val TEAM_NAME = "TEAM_NAME"
        const val TEAM_BADGE = "TEAM_BADGE"
    }
}