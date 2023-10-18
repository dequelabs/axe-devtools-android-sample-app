package com.deque.mobile.axedevtoolssampleapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.deque.mobile.devtools.AxeDevToolsCompose
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExampleComposeAccessibilityTest {
    val axeCompose = AxeDevToolsCompose()

    init {
        axeCompose.connect(BuildConfig.AXE_DEVTOOLS_APIKEY)
        BuildConfig.IS_TESTING.set(true)
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<SampleComposeActivity>()

    @Before
    fun setup() {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.waitForIdle()
    }

    @Test
    fun doGenericScan() {
        axeCompose.setComposeTestRule(composeTestRule)
        val scan = axeCompose.scan()
        scan?.uploadToDashboard()
    }
}