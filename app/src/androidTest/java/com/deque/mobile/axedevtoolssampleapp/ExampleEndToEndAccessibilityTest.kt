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
import com.deque.mobile.devtools.AxeDevTools
import com.deque.mobile.devtools.testingconfigs.AxeDevToolsEspressoConfig
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
    /***
     * Hello! Thank you for checking out the axe DevTools for Android sample app.
     * Please provide your API key in the build.gradle file in the beginning of the android block.
     * Without the API key, tests will time out and fail.
     ***/
    private val API_KEY: String = BuildConfig.AXE_DEVTOOLS_APIKEY
    /***
     * If you prefer to use user/password credentials feel free to pass those values
     * into axe.connect(username, password) as provided as a comment in the init block of this class.
     */
    init {
        axe.connect(API_KEY)
//        axe.connect(username = "", password = "")

        axe.setTestingConfig(AxeDevToolsEspressoConfig(IdlingRegistry.getInstance()))

        BuildConfig.IS_TESTING.set(true)
    }

    @Rule
    @JvmField
    val rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    /***
     * Here we have an example of a test that will scan a variety of screens and upload each scan
     * result separately.
     */
    @Test
    fun endToEndTestWithScans() {
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
            val isConnected =  axe.isUserAuthenticated()
            // Scan and receive the ScanResultHandler locally
            val scanResultHandler = axe.scan(it)

            // Upload it to the axeDevTools Mobile Dashboard
            scanResultHandler?.uploadToDashboard()

            // Use the results in your test suite
            // val result: AxeResult? = scanResultHandler?.getSerializedResult()
            // result?.axeRuleResults?.forEach { result ->
            //     if(result.status == AxeStatus.FAIL) {
            //         //uh-oh!
            //     }
            // }

            // Save the result JSON to a local file for later or local use
            // scanResultHandler?.saveResultToLocalStorage("your_file_prefix")
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