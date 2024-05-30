package com.deque.mobile.axedevtoolssampleapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deque.mobile.axedevtoolssampleapp.ui.Menu
import com.deque.mobile.devtools.AxeDevToolsCompose
import com.deque.mobile.devtools.ScanResultHandler
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
class ComposeExampleTest {
    private val axeCompose = AxeDevToolsCompose()

    init {
        axeCompose.loginWithApiKey(BuildConfig.AXE_DEVTOOLS_APIKEY)
    }

    @get:Rule val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        // Before running a scan, pass the view that you would like to scan into our setContent method
        composeTestRule.setContent { Menu() }
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.waitForIdle()

        // Pass your compose rule in here so that axe DevTools is aware of what view is being scanned
        axeCompose.setComposeTestRule(composeTestRule)
    }
    @Test
    fun composeTest() {
        // To run the test simply call scan and do one of the following with the result
        val result: ScanResultHandler? = axeCompose.scan()

        // Upload your result to the axe-mobile dashboard
        result?.uploadToDashboard()

        // Do some local verifications
//        val ruleResults: List<AxeRuleResult> = result?.serializedResult?.axeRuleResults ?: listOf()
//        var failures = 0
//        var passes = 0
//        var incomplete = 0
//        for (ruleResult in ruleResults) {
//            if (ruleResult.status == AxeStatus.FAIL) {
//                failures ++
//            }
//            if (ruleResult.status == AxeStatus.PASS) {
//                passes++
//            }
//            if (ruleResult.status == AxeStatus.INCOMPLETE) {
//                incomplete++
//            }
//        }
//        assertEquals(1, failures)

        // Save your result locally to your test device
        // result?.saveResultToLocalStorage("your_file_prefix")
    }
    @After
    fun tearDown() {
        axeCompose.tearDown()
    }
}