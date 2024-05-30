package com.deque.mobile.axedevtoolssampleapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deque.mobile.devtools.AxeDevTools
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class XmlExampleTest {
    private val axe = AxeDevTools()

    @Rule
    @JvmField
    val rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    init {
        axe.loginWithApiKey(BuildConfig.AXE_DEVTOOLS_APIKEY)
    }

    @Test
    fun xmlTest() {
        rule.scenario.onActivity { mainActivity ->
            // Scan and receive the ScanResultHandler locally
            val scanResultHandler = axe.scan(mainActivity)

            // Upload it to the axeDevTools Mobile Dashboard
            scanResultHandler?.uploadToDashboard()

            // Use the results in your test suite
//            val result: AxeResult? = scanResultHandler?.serializedResult
//            var failures = 0
//            result?.axeRuleResults?.forEach { result ->
//                if (result.status == AxeStatus.FAIL) {
//                    failures++
//                }
//            }
//            assertEquals(14, failures)

            // Save the result JSON to a local file for later or local use
            // scanResultHandler?.saveResultToLocalStorage("your_file_prefix")
            axe.tearDown()
        }
    }
}