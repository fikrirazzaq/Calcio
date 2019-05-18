package com.juvetic.calcio

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.core.presenter.pastevent.PastEventInteractor
import com.juvetic.calcio.core.presenter.pastevent.PastEventPresenter
import com.juvetic.calcio.model.event.Event
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TestPastEvent {

    @Mock
    private lateinit var contract: LeagueContract<Event>

    @Mock
    private lateinit var interactor: PastEventInteractor

    @Mock
    private lateinit var event: Event

    private lateinit var presenter: PastEventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PastEventPresenter(contract, interactor)
    }

    @Test
    fun getPastEventSuccessTest() {
        val leagueId = "4328"
        presenter.getEventDetail(leagueId)

        argumentCaptor<LeagueContract<Event?>>().apply {
            verify(interactor).getPastEvent(eq(leagueId), capture())
            firstValue.onGetDataSuccess(event)
        }

        Mockito.verify(contract).onGetDataSuccess(event)
    }

    @Test
    fun getPassEventFailedTest() {
        val leagueId = "@"
        presenter.getEventDetail(leagueId)

        argumentCaptor<LeagueContract<Event?>>().apply {
            verify(interactor).getPastEvent(eq(leagueId), capture())
            firstValue.onGetDataFailed("Not found")
        }

        Mockito.verify(contract).onGetDataFailed("Not found")
    }
}