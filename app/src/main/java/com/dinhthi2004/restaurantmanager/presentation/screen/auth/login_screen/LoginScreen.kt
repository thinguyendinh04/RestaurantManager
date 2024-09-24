package com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen.component.SignInButton
import com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen.component.TextEmailInput
import com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen.component.TextPasswordInput

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
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
            TextEmailInput()

            Spacer(modifier = Modifier.height(16.dp))
            TextPasswordInput()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Forgot password?",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Blue
                    )
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            SignInButton(navController = navController)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "OR CONTINUE WITH",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 45.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(
                        onClick = {

                        },
                        modifier = Modifier.size(56.dp),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_google),
                            contentDescription = "Google",
                            tint = Color.Red,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    IconButton(
                        onClick = {

                        },
                        modifier = Modifier.size(56.dp),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_facebook),
                            contentDescription = "Facebook",
                            tint = Color.Gray,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    IconButton(
                        onClick = {

                        },
                        modifier = Modifier.size(56.dp),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_apple),
                            contentDescription = "Apple",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(), Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account? ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 16.sp
                        ),
                        color = Color.Gray,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = "Sign up",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 18.sp,
                            color = Color.Blue
                        ),
                        modifier = Modifier.clickable(onClick = {

                        })
                    )
                }
            }
        }
    }
}
