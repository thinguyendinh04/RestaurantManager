package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.data.Employee
import com.dinhthi2004.restaurantmanager.data.employ
import com.dinhthi2004.restaurantmanager.model.Account
import com.dinhthi2004.restaurantmanager.model.AccountData
import com.dinhthi2004.restaurantmanager.model.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeEmployeeViewModel : ViewModel() {
    private val api = HttpReq.getInstance()

    val token = TokenManager.token

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _userList = MutableStateFlow<List<Account>>(emptyList())
    val userList: StateFlow<List<Account>> = _userList

    private val _accountDetail = MutableStateFlow<AccountData?>(null)
    val accountDetail: StateFlow<AccountData?> = _accountDetail

    fun getAllUser() {
        viewModelScope.launch {
            try {
                if (token != null) {
                    val userResponse = api.getUser("Bearer $token")
                    Log.d("kaka", "getAllUser: Response received")
                    if (userResponse.isSuccessful) {
                        val responseBody = userResponse.body()
                        Log.d("kaka", "getAllUser: Response body = $responseBody")

                        if (responseBody != null) {
                            _userList.value = responseBody.users ?: emptyList()
                        } else {
                            _errorMessage.value = "Response body is null"
                            Log.e("EmployeeViewModel", "getAllUser: Response body is null")
                        }
                    } else {
                        _errorMessage.value = "Failed to load users: ${userResponse.message()}"
                        Log.e("EmployeeViewModel", "getAllUser: Error = ${userResponse.message()}")
                    }
                } else {
                    _errorMessage.value = "Token is missing, please log in again."
                    Log.e("EmployeeViewModel", "getAllUser: Token is missing")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error occurred: ${e.localizedMessage}"
                Log.e("EmployeeViewModel", "getAllUser: Exception = ${e.localizedMessage}")
            }
        }
    }

    fun getUserInformation(idAccount: String) {
        viewModelScope.launch {
            try {
                if (token != null) {
                    val response = api.getUserInformation("Bearer $token", idAccount = idAccount)
                    Log.d("EmployeeViewModel", "getUserInformation: Response received for id $idAccount")
                    if (response.isSuccessful && response.body() != null) {
                        val accountDetail = response.body()?.accountDetail
                        Log.d("EmployeeViewModel", "getUserInformation: Account detail = $accountDetail")
                        _accountDetail.value = accountDetail
                    } else {
                        _errorMessage.value = "Failed to load account detail: ${response.message()}"
                        Log.e("EmployeeViewModel", "getUserInformation: Error = ${response.message()}")
                    }
                } else {
                    _errorMessage.value = "Token is missing, please log in again."
                    Log.e("EmployeeViewModel", "getUserInformation: Token is missing")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error occurred: ${e.localizedMessage}"
                Log.e("EmployeeViewModel", "getUserInformation: Exception = ${e.localizedMessage}")
            }
        }
    }
}
