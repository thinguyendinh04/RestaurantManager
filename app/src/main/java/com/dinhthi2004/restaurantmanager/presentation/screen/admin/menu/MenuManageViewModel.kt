package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class MenuManageViewModel : ViewModel() {
    private val api = HttpReq.getInstance()

    val token = TokenManager.token

    private val _mealList = MutableStateFlow<List<Meal>>(emptyList())
    val mealList: StateFlow<List<Meal>> = _mealList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _deleteMealState = MutableStateFlow<Result<String>?>(null)
    val deleteMealState: StateFlow<Result<String>?> = _deleteMealState

    private val _addMealState = MutableStateFlow<Result<String>?>(null)
    val addMealState: StateFlow<Result<String>?> = _addMealState

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun filteredItems(mealList: List<Meal>): List<Meal> {
        val query = _searchQuery.value.lowercase()
        return if (query.isEmpty()) {
            mealList
        } else {
            mealList.filter { meal ->
                meal.name.lowercase().contains(query)
            }
        }
    }


    // Fetch all meals from API
    fun getAllMeals() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (token != null) {
                    val mealResponse = api.getMeals("Bearer $token")
                    if (mealResponse.isSuccessful) {
                        Log.d("API Response", "Response Body: ${mealResponse.body()}")
                        _mealList.value = mealResponse.body()?.data ?: emptyList()
                    } else {
                        val errorBody = mealResponse.errorBody()?.string()
                        _errorMessage.value =
                            "Failed to load meals: ${errorBody ?: mealResponse.message()}"
                    }
                } else {
                    _errorMessage.value = "Token is missing, please log in again."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error occurred: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Delete a meal from API
    fun deleteMeal(mealId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (token != null) {
                    val deleteResponse = api.deleteMeal("Bearer $token", mealId)
                    if (deleteResponse.isSuccessful) {
                        _deleteMealState.value = Result.success("Meal deleted successfully")
                    } else {
                        _deleteMealState.value =
                            Result.failure(Exception("Failed to delete meal: ${errorMessage}"))
                    }
                } else {
                    _errorMessage.value = "Token is missing, please log in again."
                }
            } catch (e: Exception) {
                _deleteMealState.value =
                    Result.failure(Exception("Error occurred: ${e.localizedMessage}"))
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}

