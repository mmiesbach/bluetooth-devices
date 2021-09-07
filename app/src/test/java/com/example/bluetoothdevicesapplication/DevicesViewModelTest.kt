package com.example.bluetoothdevicesapplication

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import com.example.bluetoothdevicesapplication.ui.viewmodel.DevicesViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.robolectric.annotation.Config


@RunWith(
    RobolectricTestRunner::class)
@Config(sdk = [29])
class DevicesViewModelTest {

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { application.getSystemService(Context.BLUETOOTH_SERVICE) } returns bluetoothManager
        every { bluetoothManager.adapter } returns bluetoothAdapter
    }

    @MockK
    lateinit var application: Application

    @MockK
    lateinit var bluetoothManager: BluetoothManager

    @MockK
    lateinit var bluetoothAdapter: BluetoothAdapter

    @MockK
    lateinit var device1: BluetoothDevice

    @MockK
    lateinit var device2: BluetoothDevice

    @Test
    fun updatePairedDevicesWithNewDevice() {
        every { bluetoothAdapter.bondedDevices } returns setOf(device1) andThen setOf(device1, device2)
        val devicesViewModel = DevicesViewModel(application)

        assertTrue(devicesViewModel.updatePairedDevices())

        verify(exactly = 2) {
            bluetoothAdapter.bondedDevices
        }
    }

    @Test
    fun updatePairedDevicesWithNoNewDevice() {
        every { bluetoothAdapter.bondedDevices } returns setOf(device1, device2)
        val devicesViewModel = DevicesViewModel(application)

        assertFalse(devicesViewModel.updatePairedDevices())

        verify(exactly = 2) {
            bluetoothAdapter.bondedDevices
        }
    }

    @Test
    fun updatePairedDevicesWithBluetoothOff() {
        every { bluetoothAdapter.bondedDevices } returns setOf()
        val devicesViewModel = DevicesViewModel(application)

        assertFalse(devicesViewModel.updatePairedDevices())

        verify(exactly = 2) {
            bluetoothAdapter.bondedDevices
        }
    }

    @Test
    fun updatePairedDevicesWithBluetoothSwitchedOn() {
        every { bluetoothAdapter.bondedDevices } returns setOf() andThen setOf(device1, device2)
        val devicesViewModel = DevicesViewModel(application)

        assertTrue(devicesViewModel.updatePairedDevices())

        verify(exactly = 2) {
            bluetoothAdapter.bondedDevices
        }
    }

    @Test
    fun updatePairedDevicesWithBluetoothSwitchedOff() {
        every { bluetoothAdapter.bondedDevices } returns setOf(device1, device2) andThen setOf()
        val devicesViewModel = DevicesViewModel(application)

        assertTrue(devicesViewModel.updatePairedDevices())

        verify(exactly = 2) {
            bluetoothAdapter.bondedDevices
        }
    }
}