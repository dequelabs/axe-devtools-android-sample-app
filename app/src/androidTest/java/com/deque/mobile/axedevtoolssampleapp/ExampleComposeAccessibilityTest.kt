package com.deque.mobile.axedevtoolssampleapp

import androidx.compose.ui.test.junit4.createComposeRule
import com.deque.mobile.axedevtoolssampleapp.ui.Menu
import com.deque.mobile.devtools.AxeDevToolsCompose
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class ExampleComposeAccessibilityTest {
    val axeCompose = AxeDevToolsCompose()

    init {
        axeCompose.loginWithApiKey(BuildConfig.AXE_DEVTOOLS_APIKEY)
        BuildConfig.IS_TESTING.set(true)
    }

    @get:Rule val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            Menu()
        }
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.waitForIdle()
    }

    @Ignore
    @Test
    fun doGenericScan() {
        axeCompose.setComposeTestRule(composeTestRule)
        val scan = axeCompose.scan()
        scan?.uploadToDashboard()
    }
}