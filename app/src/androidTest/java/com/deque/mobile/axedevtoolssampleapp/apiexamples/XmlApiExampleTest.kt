package com.deque.mobile.axedevtoolssampleapp.apiexamples

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.deque.axe.android.rules.FocusableText
import com.deque.mobile.axedevtoolssampleapp.BuildConfig
import com.deque.mobile.axedevtoolssampleapp.MainActivity
import com.deque.mobile.devtools.AxeDevTools
import com.deque.mobile.devtools.testingconfigs.AxeDevToolsEspressoConfig
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class XmlApiExampleTest {

    private val axe = AxeDevTools()

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
    }

    @Test
    fun xml_tagAndSetScanName() {
        Espresso.onView(ViewMatchers.withText("Start XML")).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Next")).perform(ViewActions.click())

        //Tag API
        axe.tagScanAs(mutableSetOf("axe-sample-tag-xml"))
        //Set Scan Name API
        axe.setScanName("Axe Sample App Scan")

        rule.scenario.onActivity { mainActivity ->
            // Scan and receive the ScanResultHandler locally
            val scanResultHandler = axe.scan(mainActivity)

            // Upload it to the axeDevTools Mobile Dashboard
            scanResultHandler?.uploadToDashboard()
            axe.tearDown()
        }
    }

    @Test
    fun xml_ignoreRule() {
        Espresso.onView(ViewMatchers.withText("Start XML")).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Next")).perform(ViewActions.click())

        //Ignore Rule API
        axe.ignoreRules(listOf(FocusableText::class.java.simpleName))

        rule.scenario.onActivity { mainActivity ->
            // Scan and receive the ScanResultHandler locally
            val scanResultHandler = axe.scan(mainActivity)

            // Upload it to the axeDevTools Mobile Dashboard
            scanResultHandler?.uploadToDashboard()
            axe.tearDown()
        }
    }
}