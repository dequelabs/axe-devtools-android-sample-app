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
    fun xml_scan() {
        navigateToScreen("Start XML")

        device.wait(Until.hasObject(By.text("Next")), 1000)

        axe.scan()?.uploadToDashboard()

        navigateToScreen("Next")

        axe.scan()?.uploadToDashboard()
    }

    @Test
    fun compose_scan() {
        navigateToScreen("Start Compose")

        axe.scan()?.uploadToDashboard()
    }
}