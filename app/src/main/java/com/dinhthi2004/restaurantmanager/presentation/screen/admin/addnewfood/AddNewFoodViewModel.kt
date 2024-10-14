package com.dinhthi2004.restaurantmanager.presentation.screen.admin.addnewfood

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.model.dish_type.Dish_type
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddNewFoodViewModel : ViewModel() {
    private val api = HttpReq.getInstance()
    val token = TokenManager.token

    private val _dishTypeList = MutableStateFlow<List<Dish_type>>(emptyList())
    val Dish_Types: StateFlow<List<Dish_type>> = _dishTypeList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _addMealState = MutableStateFlow<Result<String>?>(null)
    val addMealState: StateFlow<Result<String>?> = _addMealState

    fun getAllDishType() {
        viewModelScope.launch {
            try {
                if (token != null) {
                    val response = api.getAllDishType("Bearer $token")
                    Log.d("add","${response}")
                    if (response.isSuccessful) {
                        val Dishtyperesponse = response.body()
                        Log.d("add","${Dishtyperesponse}")
                        if (Dishtyperesponse != null) {
                            _dishTypeList.value = Dishtyperesponse.data
                        } else {
                            _errorMessage.value = "Response body is null"
                        }
                    } else {
                        _errorMessage.value = "Failed to fetch dishes: ${response.message()}"
                    }
                } else {
                    _errorMessage.value = "Token is missing, please log in again."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error occurred: ${e.localizedMessage}"
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
