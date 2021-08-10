package com.example.bluetoothdevicesapplication.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bluetoothdevicesapplication.R
import com.example.bluetoothdevicesapplication.ui.viewmodel.DevicesViewModel

class DevicesFragment : Fragment() {

    companion object {
        fun newInstance() = DevicesFragment()
    }

    private lateinit var viewModel: DevicesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.devices_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DevicesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}