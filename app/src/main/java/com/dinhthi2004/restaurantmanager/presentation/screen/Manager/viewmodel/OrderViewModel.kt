package com.dinhthi2004.restaurantmanager.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.Bill
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val api = HttpReq.getInstance()
    private val TAG = "BillViewModel"

    private val _statusCode = MutableLiveData<Int>()
    val statusCode: LiveData<Int> = _statusCode

    private val _bills = MutableLiveData<List<Bill>>()
    val bills: LiveData<List<Bill>> = _bills

    private val _bill = MutableLiveData<Bill>()
    val bill: LiveData<Bill> = _bill

    fun resetStatusCode() {
        _statusCode.postValue(0)
    }

    fun getBills(token: String) {
        viewModelScope.launch {
            try {
                // Gọi API để lấy danh sách hóa đơn
                val response = api.getBills("Bearer $token")

                // Kiểm tra phản hồi và cập nhật LiveData
                if (response.message == "Bills retrieved successfully") {
                    _bills.postValue(response.data) // Sử dụng response.data cho danh sách hóa đơn
                    Log.d(TAG, "Bills retrieved successfully: ${response.data}")
                } else {
                    _bills.postValue(emptyList())
                    Log.e(TAG, "Failed to retrieve bills: ${response.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error retrieving bills: ${e.message}")
                _statusCode.postValue(500)
            }
        }
    }
}
