package org.example.moex.ui.shares

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.swipeDown
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.example.moex.R
import org.example.moex.api.NetworkException
import org.example.moex.data.SharesRepositoryProxy
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
        SharesRepositoryProxy.reset()
    }

    @Test
    fun noData() {
        activityTestRule.launchActivity(null)

        onView(withId(R.id.zeroView)).check(matches(isDisplayed()))
    }

    @Test
    fun noNetwork() {
        val error = InstrumentationRegistry.getTargetContext().resources
                .getString(R.string.error_network)

        FakeSharesRemoteDataSource.error = NetworkException(error)

        activityTestRule.launchActivity(null)

        onView(withId(R.id.zeroView)).check(matches(isDisplayed()))
        onView(withText(error)).check(matches(isDisplayed()))
    }

    @Test
    fun cachedData() {
        FakeSharesLocalDataSource.put(sampleData())

        activityTestRule.launchActivity(null)

        onView(withId(R.id.zeroView)).check(matches(not(isDisplayed())))
        onView(withText("Yandex clA")).check(matches(isDisplayed()))
    }

    @Test
    fun changeOrientation() {
        FakeSharesLocalDataSource.put(sampleData())

        // No network
        val error = "no network"
        FakeSharesRemoteDataSource.error = NetworkException(error)

        activityTestRule.launchActivity(null)

        onView(withId(R.id.refreshLayout)).perform(swipeDown())

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
