package com.deque.mobile.axedevtoolssampleapp

import android.graphics.Rect
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ScrollToAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deque.mobile.attest.AttestClient
import com.deque.mobile.attest.AxeDevTools
import com.deque.mobile.attest.testingconfigs.AttestEspressoConfig
import com.deque.mobile.auth.AccessToken
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.hamcrest.Matchers.anyOf

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleEndToEndAccessibilityTest {

    private val axe = AxeDevTools()

    init {
        axe.connect(
            BuildConfig.AXE_DEVTOOLS_ANDROID_APIKEY
        )

//        axe.setTestingConfig(AttestEspressoConfig(IdlingRegistry.getInstance()))
    }

    @Rule
    @JvmField
    val rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun endToEndTestWithScans() {
        rule.scenario.onActivity { axe.startTesting(it) }

        //Launch screen
//        runAccessibilityScan()
        onView(withText("Start")).perform(click())

        //Intro screen
//        runAccessibilityScan()
        onView(withText("Next")).perform(click())

        //Home screen
//        runAccessibilityScan()
        onView(withHint("Search")).perform(click())
//        runAccessibilityScan()
        closeSoftKeyboard()
        onView(withId(R.id.most_popular_heading)).perform(nestedScrollTo())
//        runAccessibilityScan()
        onView(withId(R.id.collection_heading)).perform(nestedScrollTo())
//        runAccessibilityScan()
        onView(withId(R.id.recommended_heading)).perform(nestedScrollTo())
//        runAccessibilityScan()

        //Catalog screen
        onView(withId(R.id.catalog)).perform(click())
//        runAccessibilityScan()
        onView(withText("Male")).perform(click())
        onView(withText("Female")).perform(click())
        onView(withText("Girl")).perform(click())
//        runAccessibilityScan()
        onView(withText("Tank Tops")).perform(nestedScrollTo())
//        runAccessibilityScan()

        //Cart screen
        onView(withId(R.id.cart)).perform(click())
        pressBack()
//        runAccessibilityScan()
//        onView(withId(R.id.cart_item_increment)).perform(click())
//        runAccessibilityScan()
//        onView(allOf(withId(R.id.cart_delete_all), withText("Delete all"))).perform(click())
//        runAccessibilityScan()

        //Menu screen
        onView(withId(R.id.menu)).perform(click())
//        runAccessibilityScan()
    }

    private fun runAccessibilityScan() {
        onView(withContentDescription("Axe")).perform(click())
    }

    private fun nestedScrollTo(): ViewAction = actionWithAssertions(NestedScrollTo())

    class NestedScrollTo : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return allOf(
                withEffectiveVisibility(Visibility.VISIBLE),
                isDescendantOfA(isAssignableFrom(NestedScrollView::class.java))
            )
        }

        override fun getDescription(): String {
            return "Scroll to on NestedScrollView"
        }

        override fun perform(uiController: UiController?, view: View?) {
                        val rect = Rect()
            view!!.getDrawingRect(rect)
            view.requestRectangleOnScreen(rect, true)
            uiController!!.loopMainThreadUntilIdle()
        }
    }
}