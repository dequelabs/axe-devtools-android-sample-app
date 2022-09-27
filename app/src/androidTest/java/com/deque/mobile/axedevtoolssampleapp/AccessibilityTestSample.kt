package com.deque.mobile.axedevtoolssampleapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deque.mobile.devtools.AxeDevTools
import com.deque.mobile.devtools.testingconfigs.AxeDevToolsEspressoConfig
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AccessibilityTestSample {
    private val axe = AxeDevTools()

    init {
        axe.connect(BuildConfig.AXE_DEVTOOLS_APIKEY)
        BuildConfig.IS_TESTING.set(true)
    }

    @Rule
    @JvmField
    val rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        axe.setTestingConfig(AxeDevToolsEspressoConfig(IdlingRegistry.getInstance()))
    }

    @Test
    fun homeScreen() {
        onView(withText("Start")).perform(click())
        onView(withText("Next")).perform(click())
    }

    @Test
    fun catalogScreen() {
        onView(withText("Start")).perform(click())
        onView(withText("Next")).perform(click())
        onView(withId(R.id.catalog)).perform(click())
    }

    @Test
    fun cartScreen() {
        onView(withText("Start")).perform(click())
        onView(withText("Next")).perform(click())
        onView(withId(R.id.cart)).perform(click())
    }

    @Test
    fun menuScreen() {
        onView(withText("Start")).perform(click())
        onView(withText("Next")).perform(click())
        onView(withId(R.id.menu)).perform(click())
    }

    @After
    fun runA11yScan() {
        rule.scenario.onActivity { mainActivity ->
            // Scan and receive the ScanResultHandler locally
            val scanResultHandler = axe.scan(mainActivity)

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
            axe.tearDown()
        }
    }
}