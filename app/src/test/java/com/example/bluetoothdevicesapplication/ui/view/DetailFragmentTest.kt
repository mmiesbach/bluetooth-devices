package com.example.bluetoothdevicesapplication.ui.view

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.bluetoothdevicesapplication.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class DetailFragmentTest {

    @Test(expected = IllegalStateException::class)
    fun fragmentStartedWithSafeArgsNull(){
        launchFragmentInContainer<DetailFragment>()
    }

    @Test
    fun fragmentStartedWithValidSafeArgs(){
        val name = "testName"
        val address = "testAddress"
        val fragmentArgs = bundleOf("deviceName" to name, "macAddress" to address)
        launchFragmentInContainer<DetailFragment>(fragmentArgs)

        onView(ViewMatchers.withId(R.id.textViewDeviceName)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(name)
            )
        )
        onView(ViewMatchers.withId(R.id.textViewMacAddress)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(address)
            )
        )
    }
}