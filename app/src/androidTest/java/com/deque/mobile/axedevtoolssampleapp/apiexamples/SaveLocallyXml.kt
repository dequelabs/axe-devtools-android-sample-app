package com.deque.mobile.axedevtoolssampleapp.apiexamples

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.deque.mobile.axedevtoolssampleapp.BuildConfig
import com.deque.mobile.axedevtoolssampleapp.MainActivity
import com.deque.mobile.axedevtoolssampleapp.R
import com.deque.mobile.devtools.AxeDevTools
import com.deque.mobile.devtools.testingconfigs.AxeDevToolsEspressoConfig
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SaveLocallyXml {
    private val axe = AxeDevTools()

    init {
        axe.loginWithApiKey(BuildConfig.AXE_DEVTOOLS_APIKEY)
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
        Espresso.onView(ViewMatchers.withText("Start XML")).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Next")).perform(ViewActions.click())
    }

    @After
    fun runA11yScan() {
        rule.scenario.onActivity { mainActivity ->
            // Scan and receive the ScanResultHandler locally
            axe.scan(mainActivity)?.saveResultToLocalStorage("axe")
        }
    }
}