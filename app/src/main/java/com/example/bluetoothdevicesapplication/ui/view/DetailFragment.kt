package com.example.bluetoothdevicesapplication.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.bluetoothdevicesapplication.R
import kotlinx.android.synthetic.main.detail_fragment.view.*

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.detail_fragment, container, false)
        view.textViewDeviceName.text = args.deviceName
        view.textViewMacAddress.text = args.macAddress

        return view
    }

}