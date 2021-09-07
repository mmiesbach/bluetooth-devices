package com.example.bluetoothdevicesapplication.util

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.bluetoothdevicesapplication.R

fun AppCompatActivity.navController(): NavController {
    val navHostFragment = this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    return navHostFragment.navController
}
