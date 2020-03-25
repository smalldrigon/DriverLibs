package com.grgbanking.driverlibs

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.grgbanking.baselibrary.util.SystemUtils

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        println("---------")
        println(SystemUtils.getDeviceId(appContext))
        println(SystemUtils.getDeviceId1(appContext))
//        assertEquals("com.grgbanking.driverlibsdemo", appContext.packageName)
    }
}
