package com.juvetic.calcio

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.core.presenter.leaguedetail.LeagueDetailInteractor
import com.juvetic.calcio.core.presenter.leaguedetail.LeagueDetailPresenter
import com.juvetic.calcio.model.league.LeagueDetail
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TestLeagueDetail {

    @Mock
    private lateinit var contract: LeagueContract<LeagueDetail>

    @Mock
    private lateinit var interactor: LeagueDetailInteractor

    @Mock
    private lateinit var leagueDetail: LeagueDetail

    private lateinit var presenter: LeagueDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LeagueDetailPresenter(contract, interactor)
    }

    @Test
    fun getLeagueDetailSuccessTest() {
        val leagueId = "4328"
        presenter.getLeagueDetail(leagueId)

        argumentCaptor<LeagueContract<LeagueDetail?>>().apply {
            verify(interactor).getLeagueDetail(eq(leagueId), capture())
            firstValue.onGetDataSuccess(leagueDetail)
        }

        Mockito.verify(contract).onGetDataSuccess(leagueDetail)
    }

    @Test
    fun getLeagueDetailFailedTest() {
        val leagueId = "@"
        presenter.getLeagueDetail(leagueId)

        argumentCaptor<LeagueContract<LeagueDetail?>>().apply {
            verify(interactor).getLeagueDetail(eq(leagueId), capture())
            firstValue.onDataError("Not found")
        }

        Mockito.verify(contract).onDataError("Not found")
    }
}