package com.example.bluetoothdevicesapplication.util

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.bluetoothdevicesapplication.R

fun Activity.navController(): NavController = this.findNavController(R.id.nav_controller_view_tag)
