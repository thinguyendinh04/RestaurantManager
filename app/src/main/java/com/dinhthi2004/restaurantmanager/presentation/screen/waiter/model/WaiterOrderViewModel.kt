package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.Bill
import com.dinhthi2004.restaurantmanager.model.Order

class WaiterOrderViewModel: ViewModel() {
    private val api = HttpReq.getInstance()

    private val token: String = ""

    private val _statuscode = MutableLiveData<Int>()
    val statuscode: LiveData<Int> = _statuscode

    private val _bills = MutableLiveData<ArrayList<Bill>>()
    val bills: LiveData<ArrayList<Bill>> = _bills

    private val _aBill = MutableLiveData<Bill?>()
    val aBill: LiveData<Bill?> = _aBill

    private val _billDetails = MutableLiveData<ArrayList<Order>>()
    val billDetails: LiveData<ArrayList<Order>> = _billDetails

    private val _aBillDetail = MutableLiveData<Order?>()
    val aBillDetail: LiveData<Order?> = _aBillDetail

}