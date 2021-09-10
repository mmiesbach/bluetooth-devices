package com.example.bluetoothdevicesapplication

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelProvider
import com.example.bluetoothdevicesapplication.ui.view.DetailFragment
import com.example.bluetoothdevicesapplication.ui.view.DevicesFragment
import com.example.bluetoothdevicesapplication.ui.viewmodel.DevicesViewModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class DevicesFragmentTest {

    @MockK
    lateinit var application: Application

    @MockK
    lateinit var devicesViewModel: DevicesViewModel

    @Before
    fun setup(){
        every {  }
    }

    @Test
    fun test(){
        launchFragmentInContainer<DevicesFragment>()

    }
}