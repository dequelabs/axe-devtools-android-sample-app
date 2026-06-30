package com.deque.mobile.axedevtoolssampleapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumentation Registry Sample Test Suite:
 *
 * The purpose of this test class is to provide a sample test suite that emulates what a typical user
 * experience might be like while running a targeted scan via the InstrumentationRegistry.
 *
 * Please follow the README to ensure that you have properly set up the build.gradle file for this feature.
 * If the project is properly set up you should see an HTML report show up in your build/reports
 * directory as well as a result uploaded to your scan result dashboard.
 *
 * Please also reference the AxeTestClass.
 */

class InstrumentationRegistryExampleTest : AxeTestClass() {

    @Rule
    @JvmField
    val rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setupAxeDevTools() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)
        val appContext = instrumentation.targetContext
        val appPackageName = appContext.packageName

        // wait for your app to load on screen
        device.wait(Until.hasObject(By.pkg(appPackageName).depth(0)), 5000)

        axe.setInstrumentation(instrumentation)
    }

    @Test
    fun test_using_registry() {
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
}