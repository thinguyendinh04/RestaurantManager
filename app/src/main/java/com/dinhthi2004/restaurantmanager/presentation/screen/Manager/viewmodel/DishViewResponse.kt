package com.dinhthi2004.restaurantmanager.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.dish.Dish
import kotlinx.coroutines.launch

class DishViewModel : ViewModel() {
    private val api = HttpReq.getInstance()
    private val TAG = "DishViewModel"

    private val _statusCode = MutableLiveData<Int>()
    val statusCode: LiveData<Int> = _statusCode

    private val _dishes = MutableLiveData<List<Dish>>()
    val dishes: LiveData<List<Dish>> = _dishes

    private val _dish = MutableLiveData<Dish?>()
    val dish: LiveData<Dish?> = _dish

    fun resetStatusCode() {
        _statusCode.postValue(0)
    }

    fun getDishDetail(token: String,id:Int) {
        viewModelScope.launch {
            try {
                // Gọi API để lấy danh sách hóa đơn
                val response = api.get1Dish("Bearer $token",id)

                // Kiểm tra phản hồi và cập nhật LiveData
                if (response.isSuccessful) {
                    _dish.postValue(response.body()?.data) // Sử dụng response.data cho danh sách hóa đơn
                    Log.d(TAG, "Bills retrieved successfully: ${response.body()}")
                } else {
                    _dish.postValue(null)
                    Log.e(TAG, "Failed to retrieve bills: ${response.body()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error retrieving bills: ${e.message}")
                _statusCode.postValue(500)
            }
        }
    }
}
