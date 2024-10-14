package com.dinhthi2004.restaurantmanager.presentation.screen.employee.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.Account
import com.dinhthi2004.restaurantmanager.model.TokenManager.token
import com.dinhthi2004.restaurantmanager.model.user.User
import com.dinhthi2004.restaurantmanager.model.user.UserRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddNewEmployeeViewModel : ViewModel() {
    private val api = HttpReq.getInstance()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _fullname = MutableStateFlow("")
    val full_name: StateFlow<String> = _fullname

//    private val _role = MutableStateFlow(2)
//    val role: StateFlow<Int> = _role

    private val _selectedRole = MutableStateFlow(3) // 3: default -> Waiter
    val selectedRole: StateFlow<Int> = _selectedRole

    fun onRoleChange(newRole: Int) {
        _selectedRole.value = newRole
    }

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    // StateFlow để theo dõi quá trình đăng ký
    private val _signupSuccess = MutableStateFlow(false)
    val signupSuccess: StateFlow<Boolean> = _signupSuccess

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun onFullnameChange(newValue: String) {
        _fullname.value = newValue
    }

    fun onEmailChange(newValue: String) {
        _email.value = newValue
    }

    fun onPasswordChange(newValue: String) {
        _password.value = newValue
    }

    fun signup() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val userRegistration = UserRegistration(
                    full_name = _fullname.value,
                    email = _email.value,
                    password = _password.value, // Lấy mật khẩu từ StateFlow
                    role = _selectedRole.value, // Sử dụng vai trò đã chọn
                )
//                Log.d("")

                var response = api.addNewUser("Bearer $token", userRegistration)
                Log.d("addNewUser", response.toString())

                if (response.isSuccessful && response.body() != null) {
                    _signupSuccess.value = true
                } else {
                    Log.d("Sign up fail", "${response.message()}")
                }
            } catch (e: Exception) {
                Log.d("Sign up fail", "${e.localizedMessage}")
            } finally {
                _loading.value = false
            }
        }
    }

}
