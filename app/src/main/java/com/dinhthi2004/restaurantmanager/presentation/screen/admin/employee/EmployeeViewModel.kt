package com.dinhthi2004.restaurantmanager.presentation.screen.admin.employee

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.Account
import com.dinhthi2004.restaurantmanager.model.AccountData
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.model.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmployeeViewModel : ViewModel() {
    private val api = HttpReq.getInstance()

    val token = TokenManager.token

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList: StateFlow<List<User>> = _userList

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun getAllUser() {
        viewModelScope.launch {
            try {
                if (!token.isNullOrBlank()) {
                    val response = api.getAllUser("Bearer $token")
                    Log.d("EmployeeViewModel", "getAllUser: Response received")

                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("EmployeeViewModel", "getAllUser: Response body = $responseBody")

                        if (responseBody != null) {
                            _userList.value = responseBody.data ?: emptyList()
                        } else {
                            _errorMessage.value = "Response body is null"
                            Log.e("EmployeeViewModel", "getAllUser: Response body is null")
                        }
                    } else {
                        _errorMessage.value = "Failed to load users: ${response.message()}"
                        Log.e("EmployeeViewModel", "getAllUser: Error = ${response.message()}")
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


    fun getUserById(id: Int) {
        viewModelScope.launch {
            try {
                if (!token.isNullOrBlank()) {
                    val response = api.getUserById("Bearer $token", id)
                    Log.d(
                        "EmployeeViewModel",
                        "getUserInformation: Response received for id $id, ${response}"
                    )

                    if (response.isSuccessful) {
                        val userResponse = response.body()
                        Log.d("userResponse", userResponse.toString())

                        if (userResponse != null) {
                            _user.value = userResponse.data
                        } else {
                            // Log dữ liệu thô nếu body null
                            val responseBodyString = response.errorBody()?.string() ?: "Empty body"
                            Log.e(
                                "EmployeeViewModel",
                                "Response body is null, raw data: $responseBodyString"
                            )
                            _errorMessage.value = "Response body is null"
                        }
                    } else {
                        _errorMessage.value = "Failed to load account detail: ${response.message()}"
                        Log.e("EmployeeViewModel", "Error = ${response.message()}")
                    }
                } else {
                    _errorMessage.value = "Token is missing, please log in again."
                    Log.e("EmployeeViewModel", "Token is missing")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error occurred: ${e.localizedMessage}"
                Log.e("EmployeeViewModel", "Exception = ${e.localizedMessage}")
            }
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            try {
                val response = api.deleteUser("Bearer $token", id)
                if (response.isSuccessful) {
                    // Sau khi xóa thành công, bạn cần tải lại danh sách người dùng
                    getAllUser()
                } else {
                    Log.d("Delete User", "Failed to delete user: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.d("Delete User", "Error: ${e.localizedMessage}")
            }
        }
    }



    // Xóa thông tin chi tiết
    fun clearAccountDetail() {
        _user.value = null
    }
}


