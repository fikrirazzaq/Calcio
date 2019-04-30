package com.juvetic.calcio

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.core.presenter.nextevent.NextEventInteractor
import com.juvetic.calcio.core.presenter.nextevent.NextEventPresenter
import com.juvetic.calcio.model.event.Event
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TestNextEvent {

    @Mock
    private lateinit var contract: LeagueContract<Event>

    @Mock
    private lateinit var interactor: NextEventInteractor

    @Mock
    private lateinit var event: Event

    private lateinit var presenter: NextEventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = NextEventPresenter(contract, interactor)
    }

    @Test
    fun getNextEventSuccessTest() {
        val leagueId = "4328"
        presenter.getNextEvent(leagueId)

        argumentCaptor<LeagueContract<Event?>>().apply {
            verify(interactor).getNextEvent(eq(leagueId), capture())
            firstValue.onGetDataSuccess(event)
        }

        Mockito.verify(contract).onGetDataSuccess(event)
    }

    @Test
    fun getNextEventFailedTest() {
        val leagueId = "@"
        presenter.getNextEvent(leagueId)

        argumentCaptor<LeagueContract<Event?>>().apply {
            verify(interactor).getNextEvent(eq(leagueId), capture())
            firstValue.onDataError("Not found")
        }

        Mockito.verify(contract).onDataError("Not found")
    }
}