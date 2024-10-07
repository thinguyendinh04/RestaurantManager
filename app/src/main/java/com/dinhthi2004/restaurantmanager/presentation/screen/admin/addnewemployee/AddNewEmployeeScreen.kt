package com.dinhthi2004.restaurantmanager.presentation.screen.admin.addnewemployee

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.employee.EmployeeViewModel
import com.dinhthi2004.restaurantmanager.presentation.screen.employee.components.EmployeeForm
import com.dinhthi2004.restaurantmanager.presentation.screen.employee.viewmodel.EmployeeFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewEmployeeScreen(
    navController: NavController,
    viewModel: EmployeeFormViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add New Employee") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmployeeForm(
                viewModel,
                onAdd = {
                    val newEmployee =
                        viewModel.createEmployee(1)
                    if (newEmployee != null) {
                        viewModel.clearForm()
                        navController.navigateUp()
                    }
                },
                onCancel = {
                    navController.navigateUp()
                }
            )
        }
    }
}