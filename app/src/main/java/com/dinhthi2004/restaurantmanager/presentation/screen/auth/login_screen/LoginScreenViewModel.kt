package com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen.data.Account
import com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen.data.getSampleAccounts
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var emailError = mutableStateOf(false)
    var passwordError = mutableStateOf(false)
    var emptyEmailError = mutableStateOf(false)
    var emptyPasswordError = mutableStateOf(false)
    var invalidCredentialsError = mutableStateOf(false)

    fun handleLogin(email: String, password: String, onLoginSuccess: (String) -> Unit) {
        resetErrors()

        if (email.isBlank()) {
            emptyEmailError.value = true
        }
        if (password.isBlank()) {
            emptyPasswordError.value = true
        }

        if (!emptyEmailError.value && !emptyPasswordError.value) {
            val account = validateAccount(email, password)
            if (account != null) {
                emailError.value = false
                passwordError.value = false
                invalidCredentialsError.value = false

                viewModelScope.launch {
                    val route = when (account.role) {
                        "Admin" -> "home_admin_screen"
                        "Manager" -> "BottomNavigation"
                        "Waiter" -> "home_waiter_screen"
                        else -> "home_user_screen"
                    }
                    onLoginSuccess(route)
                }
            } else {
                invalidCredentialsError.value = true
            }
        }
    }

    private fun validateAccount(email: String, password: String): Account? {
        val accounts = getSampleAccounts()
        return accounts.find { it.email == email && it.password == password }
    }

    private fun resetErrors() {
        emailError.value = false
        passwordError.value = false
        emptyEmailError.value = false
        emptyPasswordError.value = false
        invalidCredentialsError.value = false
    }
}
