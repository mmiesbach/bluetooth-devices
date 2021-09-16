package com.example.bluetoothdevicesapplication.ui.viewmodel

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.lifecycle.Observer
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
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
        every { observer.onChanged(any()) } answers {}
    }

    @MockK
    lateinit var application: Application

    @MockK
    lateinit var observer: Observer<List<BluetoothDevice>>

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
        devicesViewModel.pairedDevices.observeForever(observer)

        assertTrue(devicesViewModel.updatePairedDevices())

        verify(exactly = 1){ observer.onChanged(listOf(device1)) }
        verify(exactly = 1){ observer.onChanged(listOf(device1, device2)) }
        verify(exactly = 2) {
            bluetoothAdapter.bondedDevices
        }
    }

    @Test
    fun updatePairedDevicesWithNoNewDevice() {
        every { bluetoothAdapter.bondedDevices } returns setOf(device1, device2)
        val devicesViewModel = DevicesViewModel(application)
        devicesViewModel.pairedDevices.observeForever(observer)

        assertFalse(devicesViewModel.updatePairedDevices())

        verify(exactly = 2){ observer.onChanged(listOf(device1, device2)) }
        verify(exactly = 2) {
            bluetoothAdapter.bondedDevices
        }
    }

    @Test
    fun updatePairedDevicesWithBluetoothOff() {
        every { bluetoothAdapter.bondedDevices } returns setOf()
        val devicesViewModel = DevicesViewModel(application)
        devicesViewModel.pairedDevices.observeForever(observer)

        assertFalse(devicesViewModel.updatePairedDevices())

        verify(exactly = 2){ observer.onChanged(listOf()) }
        verify(exactly = 2) {
            bluetoothAdapter.bondedDevices
        }
    }

    @Test
    fun updatePairedDevicesWithBluetoothSwitchedOn() {
        every { bluetoothAdapter.bondedDevices } returns setOf() andThen setOf(device1, device2)
        val devicesViewModel = DevicesViewModel(application)
        devicesViewModel.pairedDevices.observeForever(observer)

        assertTrue(devicesViewModel.updatePairedDevices())

        verify(exactly = 1) { observer.onChanged(listOf())  }
        verify(exactly = 1){ observer.onChanged(listOf(device1, device2)) }
        verify(exactly = 2) {
            bluetoothAdapter.bondedDevices
        }
    }

    @Test
    fun updatePairedDevicesWithBluetoothSwitchedOff() {
        every { bluetoothAdapter.bondedDevices } returns setOf(device1, device2) andThen setOf()
        val devicesViewModel = DevicesViewModel(application)
        devicesViewModel.pairedDevices.observeForever(observer)

        assertTrue(devicesViewModel.updatePairedDevices())

        verify(exactly = 1) { observer.onChanged(listOf())  }
        verify(exactly = 1){ observer.onChanged(listOf(device1, device2)) }
        verify(exactly = 2) {
            bluetoothAdapter.bondedDevices
        }
    }
}