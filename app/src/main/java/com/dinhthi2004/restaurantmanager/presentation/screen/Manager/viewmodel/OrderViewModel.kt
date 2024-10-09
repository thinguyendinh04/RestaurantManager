package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data.HoaDon
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data.bill


class OrderViewModel : ViewModel() {

    val orders: List<HoaDon>
        get() = bill
}
