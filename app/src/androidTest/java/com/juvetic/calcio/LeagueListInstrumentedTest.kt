package com.juvetic.calcio

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.juvetic.calcio.ui.leaguelist.LeagueListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LeagueListInstrumentedTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(LeagueListActivity::class.java)

    @Test
    fun testLeagueListBehaviour() {
        onView(withId(R.id.rcv_league)).check(
            matches(isDisplayed())
        )
        onView(withId(R.id.rcv_league)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(6)
        )
        onView(withId(R.id.rcv_league)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                6,
                click()
            )
        )
    }
}