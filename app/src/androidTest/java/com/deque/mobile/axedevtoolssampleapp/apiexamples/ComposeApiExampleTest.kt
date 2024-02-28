package com.deque.mobile.axedevtoolssampleapp.apiexamples

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.deque.axe.android.rules.TouchSizeWcag
import com.deque.mobile.axedevtoolssampleapp.BuildConfig
import com.deque.mobile.axedevtoolssampleapp.SampleComposeActivity
import com.deque.mobile.devtools.AxeDevToolsCompose
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ComposeApiExampleTest {

    val axeCompose = AxeDevToolsCompose()

    init {
        axeCompose.loginWithApiKey(BuildConfig.AXE_DEVTOOLS_APIKEY)
        BuildConfig.IS_TESTING.set(true)
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<SampleComposeActivity>()

    @Before
    fun setup() {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.waitForIdle()
        axeCompose.resetIgnoredRules()
        axeCompose.tearDown()
    }

    @Test
    fun setScanName() {
        axeCompose.setComposeTestRule(composeTestRule)
        //Scan Name API
        axeCompose.setScanName("Axe Sample App Compose")

        val scan = axeCompose.scan()
        scan?.uploadToDashboard()
    }

    @Test
    fun setTags() {
        axeCompose.setComposeTestRule(composeTestRule)
        //Tags API
        axeCompose.tagScanAs(mutableSetOf("sample_compose_tag"))

        val scan = axeCompose.scan()
        scan?.uploadToDashboard()
    }

    @Test
    fun ignoreRules() {
        //Ignore Rules API
        axeCompose.ignoreRules(listOf(TouchSizeWcag::class.java.simpleName))

        axeCompose.setComposeTestRule(composeTestRule)

        val scan = axeCompose.scan()
        scan?.uploadToDashboard()
    }
}