package com.dinhthi2004.restaurantmanager.presentation.screen.admin.addnewfood

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.Meal
import com.dinhthi2004.restaurantmanager.model.MealType
import com.dinhthi2004.restaurantmanager.model.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddNewFoodViewModel : ViewModel() {
    private val api = HttpReq.getInstance()
    val token = TokenManager.token

    private val _mealTypes = MutableStateFlow<List<MealType>>(emptyList())
    val mealTypes: StateFlow<List<MealType>> = _mealTypes

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _addMealState = MutableStateFlow<Result<String>?>(null)
    val addMealState: StateFlow<Result<String>?> = _addMealState

    fun getAllMealTypes() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = api.getMealType("Bearer $token")
                if (response.isSuccessful) {
                    _mealTypes.value = response.body()?.data ?: emptyList()
                } else {
                    _errorMessage.value = "Failed to load meal types: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error occurred: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addNewMeal(meal: Meal) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (token != null) {
                    val response = api.addMeal("Bearer $token", meal)
                    if (response.isSuccessful) {
                        val addedMeal = response.body()!!
                        _addMealState.value = Result.success("Meal added successfully, ${addedMeal?._id}")
                    } else {
                        val errorBody = response.errorBody()?.string()
                        _addMealState.value = Result.failure(Exception("Failed to add meal: ${errorBody ?: response.message()}"))
                        Log.e("API Error", "Error adding meal: ${response.message()}")
                    }
                } else {
                    _errorMessage.value = "Token is missing, please log in again."
                }
            } catch (e: Exception) {
                _addMealState.value = Result.failure(e)
                Log.e("API Exception", "Error occurred: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun clearAddMealState() {
        _addMealState.value = null
    }
}
