package org.example.moex.ui.shares

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.example.moex.R
import org.example.moex.api.NetworkException
import org.example.moex.data.model.Share
import org.example.moex.data.source.local.FakeSharesLocalDataSource
import org.example.moex.data.source.remote.FakeSharesRemoteDataSource
import org.example.moex.rotateOrientation
import org.example.moex.ui.MainActivity
import org.hamcrest.CoreMatchers.not
import org.joda.time.DateTimeUtils
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by 5turman on 04.04.2017.
 */
@RunWith(AndroidJUnit4::class)
class SharesScreenTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    @After
    fun cleanUp() {
        FakeSharesLocalDataSource.reset()
        FakeSharesRemoteDataSource.reset()
    }

    @Test
    fun noData() {
        activityTestRule.launchActivity(null)

        onView(withId(R.id.zero_view)).check(matches(isDisplayed()))
    }

    @Test
    fun noNetwork() {
        val error = ApplicationProvider.getApplicationContext<Context>().resources
            .getString(R.string.no_connection)

        FakeSharesRemoteDataSource.error = NetworkException

        activityTestRule.launchActivity(null)

        onView(withId(R.id.zero_view)).check(matches(isDisplayed()))
        onView(withText(error)).check(matches(isDisplayed()))
    }

    @Test
    fun cachedData() {
        GlobalScope.launch(Dispatchers.Main.immediate) {
            FakeSharesLocalDataSource.put(sampleData())
        }

        activityTestRule.launchActivity(null)

        onView(withId(R.id.zero_view)).check(matches(not(isDisplayed())))
        onView(withText("Yandex clA")).check(matches(isDisplayed()))
    }

    @Test
    fun changeOrientation() {
        GlobalScope.launch(Dispatchers.Main.immediate) {
            FakeSharesLocalDataSource.put(sampleData())
        }

        // No network
        val error = ApplicationProvider.getApplicationContext<Context>().resources
            .getString(R.string.no_connection)
        FakeSharesRemoteDataSource.error = NetworkException

        activityTestRule.launchActivity(null)

        onView(withId(R.id.refresh_layout)).perform(swipeDown())

        onView(withText(error)).check(matches(isDisplayed()))
        onView(withText("Yandex clA")).check(matches(isDisplayed()))

        activityTestRule.activity.rotateOrientation()

        onView(withText("Yandex clA")).check(matches(isDisplayed()))
    }

    private fun sampleData(): List<Share> {
        val now = DateTimeUtils.currentTimeMillis()

        return listOf(
            Share("MSNG", "МосЭнерго акции обыкн.", "+МосЭнерго", now, 2.199, 3.43),
            Share("YNDX", "PLLC Yandex N.V. class A shs", "Yandex clA", now, 1266.0, -1.78),
            Share("SBER", "Сбербанк России ПАО ао", "Сбербанк", now, 149.23, -1.82)
        )
    }

}
