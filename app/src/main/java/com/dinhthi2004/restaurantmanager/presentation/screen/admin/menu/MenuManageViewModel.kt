package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.dinhthi2004.restaurantmanager.model.dish.Dish

class MenuManageViewModel : ViewModel() {
    private val api = HttpReq.getInstance()

    val token = TokenManager.token

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    private val _dishList = MutableStateFlow<List<Dish>>(emptyList())
    val dishList: StateFlow<List<Dish>> = _dishList

    private val _deleteMealState = MutableStateFlow<Result<String>?>(null)
    val deleteMealState: StateFlow<Result<String>?> = _deleteMealState

    private val _selectedDish = MutableStateFlow<Dish?>(null)
    val selectedDish: StateFlow<Dish?> = _selectedDish

    // Thêm biến trạng thái isRefreshing để quản lý việc làm mới
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    fun getDishByID(dishId: Int) {
        viewModelScope.launch {
            try {
                if (token != null) {
                    val response = api.getDishByID("Bearer $token", dishId.toString())
                    if (response.isSuccessful) {
                        _selectedDish.value = response.body() // Lưu chi tiết món ăn vào StateFlow
                    } else {
                        _errorMessage.value = "Failed to fetch dish: ${response.message()}"
                    }
                } else {
                    _errorMessage.value = "Token is missing."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage}"
            }
        }
    }

    fun getAllDishes() {
        viewModelScope.launch {
            _isRefreshing.value = true // Đặt trạng thái bắt đầu làm mới
            try {
                if (token != null) {
                    val response = api.getAllDishes("Bearer $token")
                    if (response.isSuccessful) {
                        _dishList.value = response.body()?.data ?: emptyList()
                    } else {
                        _errorMessage.value = "Failed to fetch dishes: ${response.message()}"
                    }
                } else {
                    _errorMessage.value = "Token is missing."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage}"
            } finally {
                _isRefreshing.value = false // Đặt trạng thái kết thúc làm mới
            }
        }
    }

    fun createDish(dish: Dish) {
        viewModelScope.launch {
            try {
                if (token != null) {
                    val response = api.addNewDish(
                        token = "Bearer $token",
                        dish = dish
                    )

                    if (response.isSuccessful) {
                        _successMessage.value = "Dish created successfully!"
                        getAllDishes()
                    } else {
                        _errorMessage.value = "Failed to create dish: ${response.message()}"
                    }
                } else {
                    _errorMessage.value = "Token is missing."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error occurred: ${e.localizedMessage}"
            }
        }
    }


    fun updateDish(dish: Dish) {
        viewModelScope.launch {
            try {
                if (token != null) {
                    val response = api.updateDish(
                        token = "Bearer $token",
                        dishId = dish.id.toString(),
                        dish = dish
                    )

                    if (response.isSuccessful) {
                        _successMessage.value = "Dish updated successfully!"
                    } else {
                        _errorMessage.value = "Failed to update dish: ${response.message()}"
                    }
                } else {
                    _errorMessage.value = "Token is missing."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage}"
            }
        }
    }


    fun deleteDish(id: String) {
        viewModelScope.launch {
            try {
                if (token != null) {
                    val response = api.deleteDish("Bearer $token", id)
                    if (response.isSuccessful) {
                        _successMessage.value = "Dish deleted successfully!"
                        getAllDishes()
                    } else {
                        _errorMessage.value = "Failed to delete dish: ${response.message()}"
                    }
                } else {
                    _errorMessage.value = "Token is missing, please log in again."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error occurred: ${e.localizedMessage}"
            }
        }
    }
}

