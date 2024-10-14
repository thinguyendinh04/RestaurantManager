package com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen.component.SignInButton
import com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen.component.TextEmailInput
import com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen.component.TextPasswordInput
import com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen.viewmodel.LoginScreenViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = viewModel()
) {
    var usernameState by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Text(
                text = "Hello.",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Welcome back",
                modifier = Modifier.padding(top = 12.dp),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 24.sp,
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Email Input Field
            TextEmailInput(
                username = usernameState,
                onUsernameChange = { usernameState = it }
            )

            when {
                viewModel.emptyEmailError.value -> {
                    Text(
                        text = "Email cannot be empty",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                viewModel.emailError.value -> {
                    Text(
                        text = "Please enter a valid email address",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input Field
            TextPasswordInput(
                password = passwordState,
                onPasswordChange = { passwordState = it },
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible }
            )

            when {
                viewModel.emptyPasswordError.value -> {
                    Text(
                        text = "Password cannot be empty",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                viewModel.passwordError.value -> {
                    Text(
                        text = "Password must be at least 6 characters long",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            if (viewModel.invalidCredentialsError.value) {
                Text(
                    text = "Invalid email or password",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Sign In Button
            SignInButton {
                viewModel.handleLogin(
                    email = usernameState,
                    password = passwordState
                ) { route ->
                    navController.navigate(route)
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
