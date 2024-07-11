package com.deque.mobile.axedevtoolssampleapp

import android.content.res.Resources
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.deque.axe.android.constants.AxeStatus
import com.deque.mobile.axedevtoolssampleapp.test.BuildConfig
import com.deque.mobile.devtools.AxeDevTools
import com.deque.mobile.devtools.ScanResultHandler
import com.deque.mobile.devtools.testingconfigs.AxeDevToolsEspressoConfig
import junit.framework.TestCase.assertEquals
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
        axe.tagScanAs(setOf("myAxeEspressoTest"))
        axe.setTestingConfig(
            AxeDevToolsEspressoConfig(IdlingRegistry.getInstance())
        )
        axe.setInstrumentation(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun test_using_activity() {
        rule.scenario.onActivity {
            val scanResultHandler = axe.scan(it)
            processAccessibilityResults(scanResultHandler)
        }
    }

    private fun processAccessibilityResults(scanResultHandler: ScanResultHandler?) {
        var passes = 0
        var fails = 0
        var incomplete = 0
        val result: com.deque.axe.android.AxeResult? = scanResultHandler?.serializedResult
        scanResultHandler?.uploadToDashboard()
        scanResultHandler?.saveResultToLocalStorage("axe")

        result?.axeRuleResults?.forEach { ruleResult ->
            when (ruleResult.status) {
                AxeStatus.PASS -> {
                    passes++
                }

                AxeStatus.FAIL -> {
                    fails++
                }

                AxeStatus.INCOMPLETE -> {
                    incomplete++
                }
            }
        }
        assertEquals(4, fails)
    }

    fun tearDown() {
        axe.tearDown()
    }
}