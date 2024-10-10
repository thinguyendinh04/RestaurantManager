package com.dinhthi2004.restaurantmanager.ui.screen

import android.accounts.Account
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.AccountData
import com.dinhthi2004.restaurantmanager.presentation.navigation.Routes
import com.dinhthi2004.restaurantmanager.presentation.navigation.bottomnav.BottomBar
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.employee.EmployeeViewModel
import com.dinhthi2004.restaurantmanager.ui.component.EmployeeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeScreen(
    navController: NavController,
    viewModel: EmployeeViewModel = viewModel()
) {
    val employeeList by viewModel.userList.collectAsState()
    val accountDetail by viewModel.accountDetail.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAllUser()
    }

    LaunchedEffect(accountDetail) {
        if (accountDetail != null) {
            showDialog = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Employee Management") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.ADD_NEW_EMPLOYEE)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Item"
                )
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            if (employeeList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No employees found")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(employeeList) { employee ->
                        EmployeeCard(
                            account = employee,
                            onEditClick = {
                                navController.navigate("")
                            },
                            onDeleteClick = {

                            },
                            onClick = {
                                viewModel.getUserInformation(employee._id)
                                Log.d("Screen", "Fetching info for id = ${employee._id}")
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        // Hiển thị Dialog khi showDialog là true
        if (showDialog && accountDetail != null) {
            EmployeeDetailDialog(
                accountDetail = accountDetail!!,
                onDismiss = {
                    showDialog = false
                    viewModel.clearAccountDetail()
                }
            )
        }
    }
}


@Composable
fun EmployeeDetailDialog(accountDetail: AccountData, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Employee Details")
        },
        text = {
            Column {
                Text(text = "Full Name: ${accountDetail.fullname}")
                Text(text = "Phone Number: ${accountDetail.phone_number}")
                Text(text = "Address: ${accountDetail.address}")
                Text(text = "Picture URL: ${accountDetail.picture_url}")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

