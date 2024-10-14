package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.user.User
import kotlinx.coroutines.launch

class HomeEmployeeViewModel : ViewModel() {
    private val api = HttpReq.getInstance()
    private val TAG = "HomeViewModel"
    private val _statusCode = MutableLiveData<Int>()
    private val _employees = MutableLiveData<List<User>>()
    val employees: LiveData<List<User>> = _employees

    private val _employee = MutableLiveData<User>()
    val employee: LiveData<User> = _employee
    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus: LiveData<Boolean> = _updateStatus
    fun getUserManager(token: String) {
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
                val response = api.getAllUser("Bearer $token") // Sử dụng token để gọi API

                // Kiểm tra phản hồi và cập nhật LiveData
                if (response.isSuccessful && response.body() != null) {
                    _employees.postValue(response.body()?.data) // Lấy danh sách bàn từ phản hồi
                    Log.d(TAG, "Data retrieved successfully: ${response.body()?.data}") // Log dữ liệu nhận được
                } else {
                    _employees.postValue(emptyList())
                    Log.e(TAG, "Failed to retrieve tables: ${response.message()}") // Log thông điệp lỗi
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error retrieving tables: ${e.message}") // Log lỗi
                _statusCode.postValue(500) // Cập nhật mã trạng thái lỗi
            }
        }
    }
    fun updateUser(token: String, id: Int, user: User) {
        viewModelScope.launch {
            try {
                val response = api.updateUser("Bearer $token", id, user)
                Log.d("hhh", "updateUser response: $response")

                if (response.isSuccessful) {
                    _updateStatus.value = true
                    Log.d(TAG, "User updated successfully: ${response.body()}")
                } else {
                    _updateStatus.value = false
                    Log.e(TAG, "Failed to update user: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error updating user: ${e.message}")
                _updateStatus.value = false
            }
        }
    }
    fun getUserInfo(token: String, id: Int) {
        viewModelScope.launch {
            try {
                val response = api.getInforUser("Bearer $token", id)
                if (response.isSuccessful && response.body() != null) {
                    _employee.postValue(response.body()?.data) // Cập nhật thông tin người dùng
                    Log.d("888", "User info retrieved successfully: ${response.body()?.data}")
                } else {
                    Log.e(TAG, "Failed to retrieve user info: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error retrieving user info: ${e.message}")
            }
        }
    }


}
