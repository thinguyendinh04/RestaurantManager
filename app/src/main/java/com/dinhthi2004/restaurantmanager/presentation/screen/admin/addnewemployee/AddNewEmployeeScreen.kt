package com.dinhthi2004.restaurantmanager.presentation.screen.admin.addnewemployee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.screen.employee.viewmodel.AddNewEmployeeViewModel

@Composable
fun AddNewEmployeeScreen(
    navController: NavController,
    viewModel: AddNewEmployeeViewModel = viewModel(),
) {

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val full_name by viewModel.full_name.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val signupSuccess by viewModel.signupSuccess.collectAsState()

    if (signupSuccess) {
        LaunchedEffect(Unit) {
            navController.navigateUp()
        }
    }

    var selectedRole by remember { mutableStateOf(3) } // Default: Waiter
    val roles = listOf("Admin", "Manager", "Waiter")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
        )

        // Full name
        OutlinedTextField(
            value = full_name,
            onValueChange = { viewModel.onFullnameChange(it) },
            label = { Text("Full name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown menu for Role selection
        DropdownMenuBox(selectedOption = roles[selectedRole - 1],
            options = roles,
            onOptionSelected = { role -> },
            viewModel = viewModel)

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                navController.navigateUp()
            }) {
                Text("Cancel")
            }

            Button(onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    viewModel.signup()
                } else {

                }
            }) {
                Text("Add Employee")
            }
        }
    }
}

@Composable
fun DropdownMenuBox(
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    viewModel: AddNewEmployeeViewModel
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
        .background(Color.Gray, shape = RoundedCornerShape(4.dp))
        .clickable { expanded = true }
        .padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = selectedOption, color = Color.White)
            IconButton(
                onClick = { expanded = true }, modifier = Modifier.size(24.dp)
            ) {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
            }
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEachIndexed{index, option ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(option)
                    viewModel.onRoleChange(index + 1) // Cập nhật giá trị role vào ViewModel
                    expanded = false
                }, text = { Text(text = option) })
            }
        }
    }
}
