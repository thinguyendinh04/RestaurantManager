package com.dinhthi2004.restaurantmanager.presentation.screen.admin.updatefood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.TokenManager
import kotlinx.coroutines.launch
import com.dinhthi2004.restaurantmanager.model.dish.Dish
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UpdateFoodViewModel : ViewModel() {
    private val api = HttpReq.getInstance()
    val token = TokenManager.token

    // StateFlow để lưu dữ liệu món ăn
    private val _dishState = MutableStateFlow<Dish?>(null)
    val dishState: StateFlow<Dish?> = _dishState

    // Hàm để lấy thông tin món ăn từ API
    fun getDishByID(id: String) {
        viewModelScope.launch {
            try {
                if (token != null) {
                    val response = api.getDishByID("Bearer $token", id = id)
                    if (response.isSuccessful) {
                        val result = response.body()
                        // Cập nhật dữ liệu món ăn
                        _dishState.value = result
                    } else {
                        _dishState.value = null
                    }
                }
            } catch (e: Exception) {
                _dishState.value = null
            }
        }
    }

}

