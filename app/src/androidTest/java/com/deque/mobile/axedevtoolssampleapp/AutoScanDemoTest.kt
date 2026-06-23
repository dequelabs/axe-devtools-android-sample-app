package com.deque.mobile.axedevtoolssampleapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Auto Scan Sample Test Suite:
 *
 * The purpose of this test class is to provide a sample test suite that emulates what a typical user
 * experience might be like while using our Auto Scan feature.
 *
 * Please follow the README to ensure that you have properly set up the build.gradle file for this feature.
 * If the project is properly set up for Auto Scan you should see an HTML report show up in your build/reports
 * directory as well as a result uploaded to your scan result dashboard.
 */
@RunWith(AndroidJUnit4::class)
class AutoScanDemoTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun openTab(menuItemId: Int) = onView(withId(menuItemId)).perform(click())

    private fun getString(resId: Int): String =
        composeTestRule.activity.getString(resId)

    @Test
    fun catalogTabIsShownOnLaunch() {
        // Catalog is the start destination, so its heading should be visible immediately.
        onView(withId(R.id.catalog_heading))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.catalog)))
    }

    @Test
    fun navigatingToMenuShowsComposeContent() {
        openTab(R.id.menu)

        // The Menu screen is rendered with Compose, so assert against the Compose tree.
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText(getString(R.string.customer)).assertIsDisplayed()
        composeTestRule.onNodeWithText(getString(R.string.james_anderson)).assertIsDisplayed()
        composeTestRule.onNodeWithText(getString(R.string.log_out)).assertIsDisplayed()
    }

    @Test
    fun tabSelectionStateIsRestoredWhenReturningToCatalog() {
        openTab(R.id.catalog)
        onView(withId(R.id.catalog_heading))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.catalog)))
    }
}