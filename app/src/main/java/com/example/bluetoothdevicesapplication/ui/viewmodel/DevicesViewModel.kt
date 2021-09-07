package com.example.bluetoothdevicesapplication.ui.viewmodel

import android.app.Application
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DevicesViewModel(application: Application) : AndroidViewModel(application) {
    private val _pairedDevices = MutableLiveData<List<BluetoothDevice>>()
    val pairedDevices : LiveData<List<BluetoothDevice>>
        get() = _pairedDevices

    private val bluetoothManager = application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter = bluetoothManager.adapter

    init {
        updatePairedDevices()
    }

    fun updatePairedDevices(): Boolean {
        val oldList = _pairedDevices.value
        _pairedDevices.value = bluetoothAdapter.bondedDevices.toList()
        return _pairedDevices.value!! != oldList
    }

}