package com.dinhthi2004.restaurantmanager.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.ingredient.IngredientData
import kotlinx.coroutines.launch

class IngredientViewModel : ViewModel() {
    private val api = HttpReq.getInstance()
    private val TAG = "IngredientViewModel" // Thêm tag cho log

    private val _statusCode = MutableLiveData<Int>()
    val statusCode: LiveData<Int> = _statusCode

    private val _ingredients = MutableLiveData<List<IngredientData>>()
    val ingredients: LiveData<List<IngredientData>> = _ingredients

    private val _ingredient = MutableLiveData<IngredientData>()
    val ingredient: LiveData<IngredientData> = _ingredient

    fun resetStatusCode() {
        _statusCode.postValue(0)
    }

    fun getIngredients(token: String) {
        viewModelScope.launch {
            try {
                // Gọi API để lấy danh sách nguyên liệu
                val response = api.getAllIngredient("Bearer $token")

                // Kiểm tra phản hồi và cập nhật LiveData
                if (response.isSuccessful) {
                    _ingredients.postValue(response.body()?.data)
                    Log.d(TAG, "Data retrieved successfully: ${response.body()}")
                } else {
                    _ingredients.postValue(emptyList())
                    Log.e(TAG, "Failed to retrieve ingredients: ${response.body()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error retrieving ingredients: ${e.message}") // Log lỗi
                _statusCode.postValue(500) // Cập nhật mã trạng thái lỗi
            }
        }
    }

    fun addingredient(token: String, ingredient: IngredientData, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = api.add1Ingredient("Bearer $token", ingredient)
                if (response.isSuccessful && response.body()!=null) {
                    _ingredient.postValue(response.body())
                    Log.d(TAG, "Ingredient added successfully: ${response.body()}")
                    _statusCode.postValue(200)
                    onSuccess()
                } else {
                    Log.e(TAG, "Failed to add Ingredient: ${response.body()}")
                    _statusCode.postValue(400)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error adding Ingredient: ${e.message}")
                _statusCode.postValue(500)
            }
        }
    }
}