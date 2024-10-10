package com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.model.Account
import kotlinx.coroutines.launch
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.TokenManager

class LoginViewModel : ViewModel() {
    var api = HttpReq.getInstance()

    var emailError = mutableStateOf(false)
    var passwordError = mutableStateOf(false)
    var emptyEmailError = mutableStateOf(false)
    var emptyPasswordError = mutableStateOf(false)
    var invalidCredentialsError = mutableStateOf(false)
    var isLoading = mutableStateOf(false)

    fun handleLogin(username: String, password: String, onLoginSuccess: (String) -> Unit) {
        resetErrors()

        if (username.isBlank()) {
            emptyEmailError.value = true
        }
        if (password.isBlank()) {
            emptyPasswordError.value = true
        }

        if (!emptyEmailError.value && !emptyPasswordError.value) {
            isLoading.value = true;

            // Login
            viewModelScope.launch {
                try {
                    val response = api.login(Account(username = username, password = password, role = 0, _id = ""))
//                    Log.d("response")



                    if (response.isSuccessful && response.body() != null) {
                        val account = response.body()!!

                        invalidCredentialsError.value = false;
                        emailError.value = false
                        passwordError.value = false

                        val token = account.token
                        if (token != null) {
                            Log.d("token", token)
                            TokenManager.token = token // Global token
                        }

                        val route = when (account.role) {
                            0 -> "home_admin_screen" // admin
                            1 -> "BottomNavigation" // manager
                            2 -> "WAITER_MAIN_SCREEN" // waiter
                            else -> "home_user_screen"
                        }
                        onLoginSuccess(route)
                    } else {
                        invalidCredentialsError.value = true

                    }
                } catch (e: Exception) {
                    invalidCredentialsError.value = true
                    Log.e("LoginViewModel", "Exception: ${e.message}", e)
                } finally {
                    isLoading.value = false
                }
            }
        }
    }

    private fun resetErrors() {
        emailError.value = false
        passwordError.value = false
        emptyEmailError.value = false
        emptyPasswordError.value = false
        invalidCredentialsError.value = false
    }
}
