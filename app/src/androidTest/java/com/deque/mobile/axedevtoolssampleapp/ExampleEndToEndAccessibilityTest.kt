package com.deque.mobile.axedevtoolssampleapp

import android.graphics.Rect
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deque.mobile.attest.AttestClient
import com.deque.mobile.attest.AxeDevTools
import com.deque.mobile.attest.testingconfigs.AttestEspressoConfig
import com.deque.mobile.auth.AccessToken
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

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
        //Waiting on QA Api Key
        axe.connect(
            "",
            "",
            AxeDevTools.ConnectionConfig(AttestClient.QA_REALM, AccessToken.QA_URL, AttestClient.DB_QA_URL)
        )

        axe.setTestingConfig(AttestEspressoConfig(IdlingRegistry.getInstance()))

        BuildConfig.IS_TESTING.set(true)
    }

    @Rule
    @JvmField
    val rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun endToEndTestWithScans() {
        rule.scenario.onActivity { axe.startTesting(it) }

        //Launch screen
        a11yScan()
        onView(withText("Start")).perform(click())

        //Intro screen
        a11yScan()
        onView(withText("Next")).perform(click())

        //Home screen
        a11yScan()
        onView(withHint("Search")).perform(click())
        a11yScan()
        closeSoftKeyboard()
        onView(withId(R.id.most_popular_heading)).perform(nestedScrollTo())
        a11yScan()
        onView(withId(R.id.collection_heading)).perform(nestedScrollTo())
        a11yScan()
        onView(withId(R.id.recommended_heading)).perform(nestedScrollTo())
        a11yScan()

        //Catalog screen
        onView(withId(R.id.home)).perform(click())
        onView(withId(R.id.catalog)).perform(click())
        a11yScan()
        onView(withText("Male")).perform(click())
        onView(withText("Female")).perform(click())
        onView(withText("Girl")).perform(click())
        a11yScan()
        onView(withText("Tank Tops")).perform(nestedScrollTo())
        a11yScan()

        //Cart screen
        onView(withId(R.id.cart)).perform(click())
        a11yScan()

        //Menu screen
        onView(withId(R.id.menu)).perform(click())
        a11yScan()
    }

    @After
    fun after() {
        axe.tearDown()
    }

    private fun a11yScan() {
        rule.scenario.onActivity {
            val scan = axe.scan(it)
            //Now you can do what you want with your result
            //Upload it to our backend
            scan?.uploadToDashboard()

            //Peruse the results in your test suite
//            val result: AxeResult? = scan?.getResultLocally()
//            result?.axeRuleResults?.forEach { result ->
//                if(result.status == AxeStatus.FAIL) {
//                    //uh-oh!
//                }
//            }

            //Save the result JSON to a local file for later use
//            scan?.saveResult()
        }
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