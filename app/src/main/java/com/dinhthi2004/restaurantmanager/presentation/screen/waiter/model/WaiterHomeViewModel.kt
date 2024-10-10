package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.Meal
import kotlinx.coroutines.launch

class WaiterHomeViewModel: ViewModel() {
    private val api = HttpReq.getInstance()

    private val token: String = ""

    private val _statuscode = MutableLiveData<Int>()
    val statuscode: LiveData<Int> = _statuscode

    private val _meals = MutableLiveData<ArrayList<Meal>>()
    val meals: LiveData<ArrayList<Meal>> = _meals

    private val _aMeal = MutableLiveData<Meal?>()
    val aMeal: LiveData<Meal?> = _aMeal

    fun getMeals(){
        viewModelScope.launch {
            try {
                val response = api.getMeals(token, "meal/get-meals/")
                if(response.code() == 200){
                    _meals.postValue(response.body())
                }else{
                    _meals.postValue(ArrayList())
                }
            }catch (e: RuntimeException){
                println(e)
            }
        }
    }


    fun getAMeal(id: String){
        viewModelScope.launch {
            try {
                val response = api.get1Meal(token, "meal/get-meals/${id}")
                if(response.code() == 200){
                    println(response.body())
                    _aMeal.postValue(response.body())
                }else{
                    _aMeal.postValue(null)
                }
            }catch (e: RuntimeException){
                println(e)
            }
        }
    }

    fun searchMeal(keyword: String){
        viewModelScope.launch {
            try {
                val response = api.searchMeals(token, "meal/search-meal", keyword)
                if(response.code() == 200){
                    _meals.postValue(response.body())
                }else{
                    _meals.postValue(ArrayList())
                }
            }catch (e: RuntimeException){
                println(e)
            }
        }
    }

    fun deleteMeal(id: String){
        viewModelScope.launch {
            try {
                val response = api.delete1Meal(token, "meal/delete-meal", id)
                if(response.code() == 200){
                    _aMeal.postValue(response.body())
                }else{
                    _aMeal.postValue(null)
                }
            }catch (e: RuntimeException){
                println(e)
            }
        }
    }

    fun updateMeal(id: String, newMeal: Meal){
        viewModelScope.launch {
            try {
                val response = api.update1Meal(token, "meal/update-meal", id, newMeal)
                if(response.code() == 200){
                    _aMeal.postValue(response.body())
                }else{
                    _aMeal.postValue(null)
                }
            }catch (e: RuntimeException){
                println(e)
            }
        }
    }

}