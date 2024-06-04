package com.deque.mobile.axedevtoolssampleapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.deque.mobile.axedevtoolssampleapp.test.BuildConfig
import com.deque.mobile.devtools.AxeDevTools
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class InstrumentationRegistryExampleTest {

    @Rule
    @JvmField
    val rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)


    private val axe = AxeDevTools()

    init {
        axe.loginWithApiKey(BuildConfig.AXE_DEVTOOLS_APIKEY)
    }

    @Before
    fun setupAxeDevTools() {
        axe.setInstrumentation(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun test_using_registry() {
        axe.setInstrumentation(InstrumentationRegistry.getInstrumentation())
        val scanResultHandler = axe.scan()
//      1. Upload it to the dashboard
        scanResultHandler?.uploadToDashboard()

//      2. Using the results in your test suite
//        var passes = 0
//        var fails = 0
//        var incomplete = 0
//        val result: AxeResult? = scanResultHandler?.serializedResult
//        result?.axeRuleResults?.forEach { ruleResult ->
//            when (ruleResult.status) {
//                AxeStatus.PASS -> {
//                    passes++
//                }
//
//                AxeStatus.FAIL -> {
//                    fails++
//                }
//
//                AxeStatus.INCOMPLETE -> {
//                    incomplete++
//                }
//            }
//        }
//        assertEquals(6, fails)

//        3. Save the result JSON to a local file for later use
        scanResultHandler?.saveResultToLocalStorage("axe")
    }

    @After
    fun tearDown() {
        axe.tearDown()
    }
}