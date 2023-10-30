package com.deque.mobile.axedevtoolssampleapp.apiexamples

import androidx.appcompat.app.AlertDialog
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.deque.mobile.axedevtoolssampleapp.BuildConfig
import com.deque.mobile.axedevtoolssampleapp.MainActivity
import com.deque.mobile.devtools.AxeDevTools
import com.deque.mobile.devtools.testingconfigs.AxeDevToolsEspressoConfig
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class XmlDialogScanningExampleTest {

    private val axe = AxeDevTools()

    private lateinit var countingResource: CountingIdlingResource

    init {
        axe.connect(BuildConfig.AXE_DEVTOOLS_APIKEY)
    }

    @Rule
    @JvmField
    val rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        axe.setTestingConfig(AxeDevToolsEspressoConfig(IdlingRegistry.getInstance()))
        axe.resetIgnoredRules()
        countingResource = CountingIdlingResource("DialogScan")
        IdlingRegistry.getInstance().register(countingResource)
    }

    @Test
    fun dialog_scan() {
        countingResource.increment()

        var hasResult = false

        rule.scenario.onActivity { activity ->
            val dialog = AlertDialog.Builder(activity)
                .setTitle("Title")
                .setMessage("Message")
                .show()

            axe.scan(dialog) { handler ->
                hasResult = handler?.serializedResult != null
                handler?.uploadToDashboard()
                countingResource.decrement()
            }
        }

        while(!countingResource.isIdleNow) { Thread.sleep(100) }

        Assert.assertTrue("There should be a scan result for the dialog", hasResult)
    }
}