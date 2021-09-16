package com.example.bluetoothdevicesapplication.ui.view

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bluetoothdevicesapplication.R
import com.example.bluetoothdevicesapplication.ui.viewmodel.DevicesViewModel
import kotlinx.android.synthetic.main.devices_fragment.*
import androidx.lifecycle.ViewModelProviders
import com.example.bluetoothdevicesapplication.ui.viewmodel.DevicesViewModelFactory

@SuppressLint("NotifyDataSetChanged")
class DevicesFragment : Fragment() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    lateinit var viewModel: DevicesViewModel

    private lateinit var broadcastReceiver: BroadcastReceiver
    private val intentFilter = IntentFilter()

    lateinit var viewModelFactory: DevicesViewModelFactory

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(this::viewModelFactory.isInitialized && activity != null){
            viewModelFactory = DevicesViewModelFactory(requireActivity().application)
        }
        viewModel = ViewModelProviders.of(this,
            activity?.let { DevicesViewModelFactory(it.application) }).get(DevicesViewModel::class.java)

        viewModel.pairedDevices.observe(viewLifecycleOwner, {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = DevicesRecyclerViewAdapter(it)
            noDevicesTextView.isVisible = it.isEmpty()
        })

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if(viewModel.updatePairedDevices())
                    recyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.updatePairedDevices())
            recyclerView.adapter?.notifyDataSetChanged()
        activity?.registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        activity?.unregisterReceiver(broadcastReceiver)
    }
}