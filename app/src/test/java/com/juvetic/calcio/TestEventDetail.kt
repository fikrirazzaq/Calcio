package com.juvetic.calcio

import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.core.presenter.eventdetail.EventDetailInteractor
import com.juvetic.calcio.core.presenter.eventdetail.EventDetailPresenter
import com.juvetic.calcio.model.event.Event
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TestEventDetail {

    @Mock
    private lateinit var contract: LeagueContract<Event>

    @Mock
    private lateinit var interactor: EventDetailInteractor

    @Mock
    private lateinit var event: Event

    private lateinit var presenter: EventDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventDetailPresenter(contract, interactor)
    }

    @Test
    fun getEventDetailSuccessTest() {
        val eventId = "441613"
        presenter.getEventDetail(eventId)

        argumentCaptor<LeagueContract<Event?>>().apply {
            verify(interactor).getEventDetail(eq(eventId), capture())
            firstValue.onGetDataSuccess(event)
        }

        Mockito.verify(contract).onGetDataSuccess(event)
    }

    @Test
    fun getEventDetailFailedTest() {
        val eventId = "@"
        presenter.getEventDetail(eventId)

        argumentCaptor<LeagueContract<Event?>>().apply {
            verify(interactor).getEventDetail(eq(eventId), capture())
            firstValue.onDataError("Not found")
        }

        Mockito.verify(contract).onDataError("Not found")
    }
}