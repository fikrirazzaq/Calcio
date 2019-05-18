package com.juvetic.calcio.core.presenter.playerdetail

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.model.player.PlayerDetail

class PlayerDetailPresenter(val contract: LeagueContract<PlayerDetail>, var interactor: PlayerDetailInteractor) {

    fun getPlayerDetail(id: String) {
        interactor.getPlayerDetail(id, object : LeagueContract<PlayerDetail?> {
            override fun onGetDataSuccess(data: PlayerDetail?) {
                contract.onGetDataSuccess(data)
            }

            override fun onGetDataFailed(message: String) {
                onGetDataFailed(message)
            }

        })
    }
}