package com.juvetic.calcio.core.presenter.playerlist

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.player.Player

class PlayerListPresenter(val contract: LeagueContract<Player>, var interactor: PlayerListInteractor) {

    fun getPlayerList(teamId: String) {
        interactor.getPlayerList(teamId, object : LeagueContract<Player?> {
            override fun onGetDataSuccess(data: Player?) {
                contract.onGetDataSuccess(data)
            }

            override fun onGetDataFailed(message: String) {
                onGetDataFailed(message)
            }

        })
    }
}