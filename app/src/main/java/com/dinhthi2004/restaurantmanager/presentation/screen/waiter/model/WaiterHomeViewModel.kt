package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.Meal
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.model.dish.Dish
import kotlinx.coroutines.launch

class WaiterHomeViewModel : ViewModel() {
    private val api = HttpReq.getInstance()

    val token = TokenManager.token

    private val _statuscode = MutableLiveData<Int>()
    val statuscode: LiveData<Int> = _statuscode

    private val _meals = MutableLiveData<List<Dish>>()
    val meals: LiveData<List<Dish>> = _meals

    private val _aMeal = MutableLiveData<Dish?>()
    val aMeal: LiveData<Dish?> = _aMeal

    fun getMeals() {
        viewModelScope.launch {
            try {
                val response = api.getAllDishes("Bearer $token")
                if (response.code() == 200) {
                    _meals.postValue(response.body()?.data)
                } else {
                    _meals.postValue(ArrayList())
                }
            } catch (e: RuntimeException) {
                println(e)
            }
        }
    }


    fun getAMeal(id: String) {
        viewModelScope.launch {
            try {
                val response = api.getDishByID("Bearer $token", id)
                if (response.code() == 200) {
                    println(response.body())
                    _aMeal.postValue(response.body())
                } else {
                    _aMeal.postValue(null)
                }
            } catch (e: RuntimeException) {
                println(e)
            }
        }
    }

//    fun searchMeal(keyword: String) {
//        viewModelScope.launch {
//            try {
//                val response = api.searchMeals("Bearer $token", "meal/search-meal", keyword)
//                if (response.code() == 200) {
//                    _meals.postValue(response.body())
//                } else {
//                    _meals.postValue(ArrayList())
//                }
//            } catch (e: RuntimeException) {
//                println(e)
//            }
//        }
//    }

    fun deleteMeal(id: String) {
        viewModelScope.launch {
            try {
                val response = api.deleteDish("Bearer $token", id)
                if (response.code() == 200) {
                    _aMeal.postValue(response.body())
                } else {
                    _aMeal.postValue(null)
                }
            } catch (e: RuntimeException) {
                println(e)
            }
        }
    }

//    fun updateMeal(id: String, newMeal: Meal) {
//        viewModelScope.launch {
//            try {
//                val response = api.updateDish("Bearer $token", "meal/update-meal", id, newMeal)
//                if (response.code() == 200) {
//                    _aMeal.postValue(response.body())
//                } else {
//                    _aMeal.postValue(null)
//                }
//            } catch (e: RuntimeException) {
//                println(e)
//            }
//        }
//    }

}