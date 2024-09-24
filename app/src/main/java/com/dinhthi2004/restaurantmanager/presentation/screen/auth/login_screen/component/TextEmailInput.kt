package com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.dinhthi2004.restaurantmanager.R

@Preview
@Composable
fun TextEmailInput() {
    var email by remember { mutableStateOf("") }

    Column {
        Text("Email", color = Color.Black)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    "Enter email", style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.Gray
                    )
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = "Email icon"
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
    }
}