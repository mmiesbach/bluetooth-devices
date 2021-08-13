package com.example.bluetoothdevicesapplication.ui.view

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bluetoothdevicesapplication.R
import com.example.bluetoothdevicesapplication.ui.viewmodel.DevicesViewModel
import kotlinx.android.synthetic.main.devices_fragment.*
import kotlinx.android.synthetic.main.devices_fragment.view.*

class DevicesFragment : Fragment() {

    private lateinit var viewModel: DevicesViewModel
    private lateinit var broadcastReceiver: BroadcastReceiver
    private val intentFilter = IntentFilter()

    init {
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.devices_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DevicesViewModel::class.java)
        viewModel.pairedDevices.observe(viewLifecycleOwner, {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = DevicesRecyclerViewAdapter(it)
            noDevicesTextView.isVisible = it.isEmpty()
        })

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                    viewModel.getPairedDevices()
                    recyclerView.adapter?.notifyDataSetChanged()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getPairedDevices()
        recyclerView.adapter?.notifyDataSetChanged()
        activity?.registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        activity?.unregisterReceiver(broadcastReceiver)
    }

}