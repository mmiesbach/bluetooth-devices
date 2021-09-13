package com.example.bluetoothdevicesapplication.ui.view

import android.bluetooth.BluetoothDevice
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.bluetoothdevicesapplication.R
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class DevicesFragmentTest {

    @MockK
    lateinit var device1: BluetoothDevice

    @MockK
    lateinit var device2: BluetoothDevice

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        every { device1.name } returns "testDevice1"
        every { device2.name } returns "testDevice2"
        every { device1.javaClass.getMethod("isConnected").invoke(device1)} returns true
        every { device2.javaClass.getMethod("isConnected").invoke(device2)} returns true
    }

    @Test
    fun itemCountWithTwoDevices(){
        val scenario = launchFragmentInContainer<DevicesFragment>()
        scenario.onFragment{
            it.viewModel.mutablePairedDevices.value = listOf(device1, device2)
            val recyclerView = it.activity?.findViewById<RecyclerView>(R.id.recyclerView)
            if (recyclerView != null) {
                recyclerView.adapter?.let { it1 -> assertEquals(2, it1.itemCount) }
            }
        }
    }

    @Test
    fun itemCountWithOneDevice(){
        val scenario = launchFragmentInContainer<DevicesFragment>()
        scenario.onFragment{
            it.viewModel.mutablePairedDevices.value = listOf(device1)
            val recyclerView = it.activity?.findViewById<RecyclerView>(R.id.recyclerView)
            if (recyclerView != null) {
                recyclerView.adapter?.let { it1 -> assertEquals(1, it1.itemCount
                ) }
            }
        }
    }

    @Test
    fun deviceTextIsShown(){
        val scenario = launchFragmentInContainer<DevicesFragment>()
        scenario.onFragment{
            it.viewModel.mutablePairedDevices.value = listOf(device1, device2)
            onView(withText("testDevice1")).check(matches(isDisplayed()))
            onView(withText("testDevice2")).check(matches(isDisplayed()))
        }
    }

    @Test
    fun noDevicePairedTextIsNotShown(){
        val scenario = launchFragmentInContainer<DevicesFragment>()
        scenario.onFragment{
            it.viewModel.mutablePairedDevices.value = listOf(device1, device2)
            onView(withText("No paired devices detected. Bluetooth has to be activated in order to display your paired devices.")).check(matches(not(isDisplayed())
            ))
        }
    }

    @Test
    fun noDevicePairedTestIsShown(){
        val scenario = launchFragmentInContainer<DevicesFragment>()
        scenario.onFragment{
            it.viewModel.mutablePairedDevices.value = listOf()
            onView(withText("No paired devices detected. Bluetooth has to be activated in order to display your paired devices.")).check(matches(isDisplayed()
            ))
        }
    }
}