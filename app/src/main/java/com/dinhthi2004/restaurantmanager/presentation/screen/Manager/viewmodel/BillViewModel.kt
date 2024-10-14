package com.dinhthi2004.restaurantmanager.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.bill.BillData
import kotlinx.coroutines.launch

class BillViewModel : ViewModel() {
    private val api = HttpReq.getInstance()
    private val TAG = "BillViewModel"

    private val _statusCode = MutableLiveData<Int>()
    val statusCode: LiveData<Int> = _statusCode

    private val _bills = MutableLiveData<List<BillData>>()
    val bills: LiveData<List<BillData>> = _bills

    private val _bill = MutableLiveData<BillData>()
    val bill: LiveData<BillData> = _bill

    fun resetStatusCode() {
        _statusCode.postValue(0)
    }

    fun getBills(token: String) {
        viewModelScope.launch {
            try {
                // Gọi API để lấy danh sách hóa đơn
                val response = api.getAllBill("Bearer $token")

                // Kiểm tra phản hồi và cập nhật LiveData
                if (response.isSuccessful) {
                    _bills.postValue(response.body()?.data) // Sử dụng response.data cho danh sách hóa đơn
                    Log.d(TAG, "Bills retrieved successfully: ${response.body()}")
                } else {
                    _bills.postValue(emptyList())
                    Log.e(TAG, "Failed to retrieve bills: ${response.body()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error retrieving bills: ${e.message}")
                _statusCode.postValue(500)
            }
        }
    }
}
