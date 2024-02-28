package com.deque.mobile.axedevtoolssampleapp.apiexamples

import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.deque.axe.android.AxeResult
import com.deque.axe.android.moshi.MoshiConfig
import com.deque.mobile.axedevtoolssampleapp.BuildConfig
import com.deque.mobile.devtools.AxeDevTools
import com.deque.networking.AxeLogger
import com.deque.networking.analytics.AmplitudeEventProps
import com.deque.networking.analytics.AmplitudeEventType
import com.deque.networking.analytics.AnalyticsService
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.IOException


private const val BASIC_SAMPLE_PACKAGE = "com.deque.mobile.axedevtoolssampleapp"
private const val LAUNCH_TIMEOUT = 5000L
class SaveLocallyExampleTest {

    private lateinit var device: UiDevice
    private lateinit var registry: Instrumentation

    companion object {
        val axe = AxeDevTools()

        init {
            axe.loginWithApiKey(BuildConfig.AXE_DEVTOOLS_APIKEY)
            BuildConfig.IS_TESTING.set(true)
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
    fun scan_saveResultLocally() {
        device.findObject(By.text("Start Compose")).click()

        device.wait(Until.hasObject(By.text("Profile")), 1000)

        val axeResult = axe.scan()?.serializedResult!!

        val path = registry.targetContext.getExternalFilesDir(null) ?: File("")
        val prefix = "axe"

        try {
            val dir = File(path, "AxeTestCases").also { it.mkdirs() }
            val file = File(dir, "$prefix-${System.currentTimeMillis()}-axe-result.json")
            file.appendText(MoshiConfig.prettyPrint(axeResult, AxeResult::class.java))
            AxeLogger.axeResultSavedAt(file.absolutePath)
        } catch (exception: IOException) {
            AxeLogger.errorWhileSaving(exception)

            AnalyticsService.sendEvent(
                AmplitudeEventType.SCAN_SAVE_ERROR,
                AmplitudeEventProps(reason = exception.message)
            )
        }
    }
}