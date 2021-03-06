package com.example.bluetoothdevicesapplication.ui.viewmodel

import android.app.Application
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DevicesViewModel(application: Application) : AndroidViewModel(application) {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val mutablePairedDevices = MutableLiveData<List<BluetoothDevice>>()

    val pairedDevices : LiveData<List<BluetoothDevice>>
        get() = mutablePairedDevices

    private val bluetoothManager = application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter = bluetoothManager.adapter

    init {
        updatePairedDevices()
    }

    fun updatePairedDevices(): Boolean {
        val oldList = mutablePairedDevices.value
        mutablePairedDevices.value = bluetoothAdapter.bondedDevices.toList()
        return mutablePairedDevices.value!! != oldList
    }

}