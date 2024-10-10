package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.Meal
import com.dinhthi2004.restaurantmanager.model.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

    fun getAllMeals() {
        viewModelScope.launch {
            try {
                if (token != null) {
                    val mealResponse = api.getMeals(
                        "Bearer $token"
                    )
                    if (mealResponse.isSuccessful) {
                        _mealList.value = mealResponse.body()?.data ?: emptyList()
                    } else {
                        _errorMessage.value = "Failed to load meals: ${mealResponse.message()}"
                    }
                } else {
                    _errorMessage.value = "Token is missing, please log in again."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error occurred: ${e.localizedMessage}"
            }
        }
    }

    fun deleteMeal(mealId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (token != null) {
                    val deleteResponse = api.deleteMeal("Bearer $token", mealId)
                    if (deleteResponse.isSuccessful) {
                        _deleteMealState.value = Result.success("Meal deleted successfully")
                        getAllMeals()
                    } else {
                        _deleteMealState.value = Result.failure(Exception("Failed to delete meal: ${deleteResponse.message()}"))
                    }
                } else {
                    _errorMessage.value = "Token is missing, please log in again."
                }
            } catch (e: Exception) {
                _deleteMealState.value = Result.failure(Exception("Error occurred: ${e.localizedMessage}"))
            } finally {
                _isLoading.value = false
            }
        }
    }
}
