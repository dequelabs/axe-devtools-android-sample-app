package com.deque.mobile.axedevtoolssampleapp.accessibilityuiautomator

import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Test

/**
 * Simple example to run Accessibility Scans in UiAutomator test suite
 * and upload results to dashboard.
 */
class AccessibilityUiAutomatorUploadResultTest: BaseInstrumentationTest() {

    @Test
    fun ui_automator_scan() {
        axe.scan()?.uploadToDashboard()
    }
}