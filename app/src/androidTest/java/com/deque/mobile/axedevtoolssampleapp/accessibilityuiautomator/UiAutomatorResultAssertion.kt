package com.deque.mobile.axedevtoolssampleapp.accessibilityuiautomator

import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.deque.axe.android.constants.AxeStatus
import junit.framework.TestCase.assertEquals
import org.junit.Ignore
import org.junit.Test

/**
 * uiAutomator test to make assertions on the accessibility scan result.
 * Note: This test does not upload results to the dashboard.
 */
class UiAutomatorResultAssertion: BaseInstrumentationTest() {

    @Ignore
    @Test
    fun xml_scan() {
        axe.scan()?.serializedResult?.axeRuleResults?.forEach {
            if (it.status == AxeStatus.INCOMPLETE) {
                println("Needs more review")
            } else {
                assertEquals(AxeStatus.PASS, it.status)
            }
        }

    }
}