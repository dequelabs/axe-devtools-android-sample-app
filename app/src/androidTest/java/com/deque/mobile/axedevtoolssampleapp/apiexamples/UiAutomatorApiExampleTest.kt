package com.deque.mobile.axedevtoolssampleapp.apiexamples

import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.deque.axe.android.rules.FocusableText
import com.deque.mobile.axedevtoolssampleapp.BuildConfig
import com.deque.mobile.devtools.AxeDevTools
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test

private const val BASIC_SAMPLE_PACKAGE = "com.deque.mobile.axedevtoolssampleapp"
private const val LAUNCH_TIMEOUT = 5000L

class UiAutomatorApiExampleTest {

    private lateinit var device: UiDevice
    private lateinit var registry: Instrumentation

    companion object {
        val axe = AxeDevTools()

        init {
            axe.connect(BuildConfig.AXE_DEVTOOLS_APIKEY)
            axe.resetIgnoredRules()
        }
    }

    @Before
    fun setup() {
        registry = InstrumentationRegistry.getInstrumentation()
        device = UiDevice.getInstance(registry)
        device.pressHome()

        val launcherPackage: String = device.launcherPackageName
        ViewMatchers.assertThat(launcherPackage, CoreMatchers.notNullValue())
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            LAUNCH_TIMEOUT
        )

        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = registry.context.packageManager.getLaunchIntentForPackage(
            BASIC_SAMPLE_PACKAGE
        )?.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)

        device.wait(
            Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
            LAUNCH_TIMEOUT
        )

        axe.setInstrumentation(registry)
        axe.resetIgnoredRules()
    }

    @Test
    fun scan_tagAndSetScanName() {
        //Set Scan Name API
        axe.setScanName("Axe Sample App UiAutomator")
        //Tags API
        axe.tagScanAs(mutableSetOf("tag_ui_aut"))

        device.findObject(By.text("Start Compose")).click()

        device.wait(Until.hasObject(By.text("Profile")), 1000)

        axe.scan()?.uploadToDashboard()
    }

    @Test
    fun scan_ignoreRule() {
        //Ignore Rules API
        axe.ignoreRules(mutableListOf( FocusableText::class.java.simpleName))

        device.findObject(By.text("Start Compose")).click()

        device.wait(Until.hasObject(By.text("Profile")), 1000)

        axe.scan()?.uploadToDashboard()
    }
}