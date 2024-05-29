package com.deque.mobile.axedevtoolssampleapp.apiexamples

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.deque.axe.android.AxeResult
import com.deque.axe.android.constants.AxeStatus
import com.deque.mobile.axedevtoolssampleapp.MainActivity
import com.deque.mobile.axedevtoolssampleapp.test.BuildConfig
import com.deque.mobile.devtools.AxeDevTools
import com.deque.networking.interfaces.AxeDevToolsClient
import com.deque.networking.models.auth.AccessToken
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class InstrumentationRegistryExampleTest {

    @Rule
    @JvmField
    val rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    val axe = AxeDevTools()

    init {
        axe.loginWithApiKey(BuildConfig.AXE_DEVTOOLS_APIKEY, AxeDevToolsClient.DB_QA_URL)
    }

    @Before
    fun setupAxeDevTools() {
    }

    @Ignore
    @Test
    fun test_using_registry() {
        a11yScan()
    }

    private fun a11yScan() {
        val registry = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(registry)
        device.wait(Until.hasObject(By.text("Catalog")), 1000)
        var passes = 0
        var fails = 0
        var incomplete = 0
        runBlocking { delay(1000L) }

        val root = InstrumentationRegistry.getInstrumentation().uiAutomation.rootInActiveWindow
        axe.setInstrumentation(InstrumentationRegistry.getInstrumentation())
        assert(root != null)
        InstrumentationRegistry.getInstrumentation().uiAutomation.windows
        runBlocking { delay(1000L) }
        val scanResultHandler = axe.scan()
        //1. Upload it to the dashboard
        scanResultHandler?.uploadToDashboard()

        //2. Using the results in your test suite
        val result: AxeResult? = scanResultHandler?.serializedResult
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
        assertEquals(0, fails)

        //3. Save the result JSON to a local file for later use
        scanResultHandler?.saveResultToLocalStorage("your_file_prefix")
    }

    @After
    fun tearDown() {
        axe.tearDown()
    }
}