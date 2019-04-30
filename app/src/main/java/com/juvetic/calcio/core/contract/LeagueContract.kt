package com.juvetic.calcio.core.contract

interface LeagueContract<T> {

    fun onGetDataSuccess(data: T?)
    fun onDataError(message: String)
}