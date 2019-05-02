package com.juvetic.calcio

import com.juvetic.calcio.core.presenter.eventteamhomeaway.TeamAwayContract
import com.juvetic.calcio.core.presenter.eventteamhomeaway.TeamDetailInteractor
import com.juvetic.calcio.core.presenter.eventteamhomeaway.TeamDetailPresenter
import com.juvetic.calcio.core.presenter.eventteamhomeaway.TeamHomeContract
import com.juvetic.calcio.model.team.Team
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TestTeamDetail {

    @Mock
    private lateinit var homeContract: TeamHomeContract

    @Mock
    private lateinit var awayContract: TeamAwayContract

    @Mock
    private lateinit var interactor: TeamDetailInteractor

    @Mock
    private lateinit var team: Team

    private lateinit var presenter: TeamDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamDetailPresenter(homeContract, awayContract, interactor)
    }

    @Test
    fun getHomeTeamDetailSuccessTest() {
        val leagueId = "133604"
        presenter.getHomeTeamDetail(leagueId)

        argumentCaptor<TeamHomeContract>().apply {
            verify(interactor).getHomeTeamDetail(eq(leagueId), capture())
            firstValue.onGetHomeDataSuccess(team)
        }

        Mockito.verify(homeContract).onGetHomeDataSuccess(team)
    }

    @Test
    fun getAwayTeamDetailSuccessTest() {
        val leagueId = "133604"
        presenter.getAwayTeamDetail(leagueId)

        argumentCaptor<TeamAwayContract>().apply {
            verify(interactor).getAwayTeamDetail(eq(leagueId), capture())
            firstValue.onGetAwayDataSuccess(team)
        }

        Mockito.verify(awayContract).onGetAwayDataSuccess(team)
    }

    @Test
    fun getHomeTeamDetailFailedTest() {
        val leagueId = "@"
        presenter.getHomeTeamDetail(leagueId)

        argumentCaptor<TeamHomeContract>().apply {
            verify(interactor).getHomeTeamDetail(eq(leagueId), capture())
            firstValue.onGetHomeDataFailed("Not found")
        }

        Mockito.verify(homeContract).onGetHomeDataFailed("Not found")
    }

    @Test
    fun getAwayTeamDetailFailedTest() {
        val leagueId = "@"
        presenter.getAwayTeamDetail(leagueId)

        argumentCaptor<TeamAwayContract>().apply {
            verify(interactor).getAwayTeamDetail(eq(leagueId), capture())
            firstValue.onGetAwayDataFailed("Not found")
        }

        Mockito.verify(awayContract).onGetAwayDataFailed("Not found")
    }
}