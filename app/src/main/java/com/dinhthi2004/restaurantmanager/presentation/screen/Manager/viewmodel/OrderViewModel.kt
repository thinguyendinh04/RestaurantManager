package com.dinhthi2004.restaurantmanager.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.OrderData
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val api = HttpReq.getInstance()
    private val TAG = "BillViewModel"

    private val _statusCode = MutableLiveData<Int>()
    val statusCode: LiveData<Int> = _statusCode

    private val _orders = MutableLiveData<List<OrderData>>()
    val orders: LiveData<List<OrderData>> = _orders

    private val _order = MutableLiveData<OrderData?>()
    val order: LiveData<OrderData?> = _order

    fun resetStatusCode() {
        _statusCode.postValue(0)
    }

    fun getOrderDetail(token: String,id:Int) {
        viewModelScope.launch {
            try {
                // Gọi API để lấy danh sách hóa đơn
                val response = api.get1Order("Bearer $token",id)

                // Kiểm tra phản hồi và cập nhật LiveData
                if (response.isSuccessful) {
                    _order.postValue(response.body()?.data) // Sử dụng response.data cho danh sách hóa đơn
                    Log.d(TAG, "Bills retrieved successfully: ${response.body()}")
                } else {
                    _order.postValue(null)
                    Log.e(TAG, "Failed to retrieve bills: ${response.body()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error retrieving bills: ${e.message}")
                _statusCode.postValue(500)
            }
        }
    }
}
