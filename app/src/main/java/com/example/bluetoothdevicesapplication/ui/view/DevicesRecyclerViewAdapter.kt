package com.example.bluetoothdevicesapplication.ui.view

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetoothdevicesapplication.R
import kotlinx.android.synthetic.main.list_item.view.*
import java.lang.reflect.Method

class DevicesRecyclerViewAdapter(private val pairedDevices: List<BluetoothDevice>) :
    RecyclerView.Adapter<DevicesRecyclerViewAdapter.DevicesRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item ,parent, false)

        return DevicesRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: DevicesRecyclerViewHolder, position: Int) {
        val currentDevice = pairedDevices[position]
        holder.textView.text = currentDevice.name
        val m: Method = currentDevice.javaClass.getMethod("isConnected")
        val connected = m.invoke(currentDevice) as Boolean
        if(connected) {
            holder.connectedCircle.isVisible = true
        }
    }

    override fun getItemCount(): Int {
        return pairedDevices.size
    }

   inner class DevicesRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{

        val textView : TextView
        val connectedCircle : ImageView
        private val currentView = view

        init {
            view.setOnClickListener(this)
            textView = view.deviceListItemTextView
            connectedCircle = view.connectedCircle
        }

        override fun onClick(p0: View?) {
            val action = DevicesFragmentDirections.actionDevicesFragmentToDetailFragment(pairedDevices[adapterPosition].name, pairedDevices[adapterPosition].address)
            Navigation.findNavController(currentView).navigate(action)
        }

    }
}