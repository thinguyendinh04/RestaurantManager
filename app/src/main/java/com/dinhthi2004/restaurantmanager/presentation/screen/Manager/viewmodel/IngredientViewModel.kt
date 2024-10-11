package com.dinhthi2004.restaurantmanager.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.Ingredient
import com.dinhthi2004.restaurantmanager.model.Table
import kotlinx.coroutines.launch

class IngredientViewModel : ViewModel() {
    private val api = HttpReq.getInstance()
    private val TAG = "IngredientViewModel" // Thêm tag cho log

    private val _statusCode = MutableLiveData<Int>()
    val statusCode: LiveData<Int> = _statusCode

    private val _ingredients = MutableLiveData<List<Ingredient>>()
    val ingredients: LiveData<List<Ingredient>> = _ingredients

    private val _ingredient = MutableLiveData<Ingredient>()
    val ingredient: LiveData<Ingredient> = _ingredient

    fun resetStatusCode() {
        _statusCode.postValue(0)
    }

    fun getIngredients(token: String) {
        viewModelScope.launch {
            try {
                // Gọi API để lấy danh sách nguyên liệu
                val response = api.getIngredients("Bearer $token")

                // Kiểm tra phản hồi và cập nhật LiveData
                if (response.message == "Ingredients retrieved successfully") {
                    _ingredients.postValue(response.data)
                    Log.d(
                        TAG,
                        "Data retrieved successfully: ${response.data}"
                    ) // Log dữ liệu nhận được
                } else {
                    _ingredients.postValue(emptyList())
                    Log.e(
                        TAG,
                        "Failed to retrieve ingredients: ${response.message}"
                    ) // Log thông điệp lỗi
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error retrieving ingredients: ${e.message}") // Log lỗi
                _statusCode.postValue(500) // Cập nhật mã trạng thái lỗi
            }
        }
    }

    fun addingredient(token: String, ingredient: Ingredient, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = api.addIngredient("Bearer $token", ingredient)
                if (response.message == "Table added successfully") {
                    _ingredient.postValue(response.data)
                    Log.d(TAG, "Table added successfully: ${response.data}")
                    _statusCode.postValue(200)
                    onSuccess()
                } else {
                    Log.e(TAG, "Failed to add table: ${response.message}")
                    _statusCode.postValue(400)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error adding table: ${e.message}")
                _statusCode.postValue(500)
            }
        }
    }
}