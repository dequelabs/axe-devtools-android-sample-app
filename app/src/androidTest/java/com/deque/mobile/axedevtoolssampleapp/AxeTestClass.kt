package com.deque.mobile.axedevtoolssampleapp

import com.deque.mobile.axedevtoolssampleapp.test.BuildConfig
import com.deque.mobile.devtools.AxeDevTools
import org.junit.AfterClass

abstract class AxeTestClass {

    init {
        /**
         * Start a session on axe Developer Hub with a valid API key and your project ID. If
         * a session has already been started for this test suite, that session will be used instead
         * of creating a new session.
         *
         * You can find and create a projects here : https://axe.deque.com/axe-watcher
         */

        axe.startScanSession(
            apiKey = BuildConfig.AXE_DEVTOOLS_APIKEY,
            projectId = BuildConfig.AXE_DEVTOOLS_PROJECT_ID
        )
    }

    companion object {
        val axe = AxeDevTools()

        @JvmStatic
        @AfterClass
        fun afterClass() {
            axe.tearDown()

            // Generates a report of all scans in a given test class to a report that
            // tells you which accessibility violations we found.
            axe.generateHtmlReportAndSummary("name")
        }
    }
}