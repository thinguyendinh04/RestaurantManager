package com.dinhthi2004.restaurantmanager.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.user.User
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
    val user by viewModel.user.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showUpdateRoleDialog by remember { mutableStateOf(false) } // Thêm biến này để hiển thị UpdateRoleDialog
    var userToDelete by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getAllUser()
    }

    LaunchedEffect(user) {
        if (user != null) {
            showDialog = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Employee Management") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                },
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
                            user = employee,
                            onDeleteClick = {
                                userToDelete = employee
                                showDeleteDialog = true
                            },
                            onClick = {
                                viewModel.getUserById(employee.id)
                                Log.d("Screen", "Fetching info for id = ${employee.id}")
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        // Hiển thị Dialog chi tiết nhân viên
        if (showDialog && user != null) {
            EmployeeDetailDialog(
                user = user!!,
                onDismiss = {
                    showDialog = false
                    viewModel.clearAccountDetail()
                },
                onUpdateRoleClick = {
                    showUpdateRoleDialog = true // Hiển thị UpdateRoleDialog khi nhấn "Update Role"
                }
            )
        }

        // Hiển thị Dialog cập nhật vai trò
        if (showUpdateRoleDialog && user != null) {
            UpdateRoleDialog(
                user = user!!,
                onDismiss = {
                    showUpdateRoleDialog = false
                },
                onUpdateRole = { newRole ->
                    // Gửi vai trò mới cho ViewModel để cập nhật role
                    //Hafaham update Role
                }
            )
        }

        // Hiển thị Dialog xác nhận xóa
        if (showDeleteDialog && userToDelete != null) {
            ConfirmDeleteDialog(
                user = userToDelete!!,
                onConfirm = {
                    viewModel.deleteUser(userToDelete!!.id)
                    showDeleteDialog = false
                },
                onDismiss = {
                    showDeleteDialog = false
                }
            )
        }
    }
}


@Composable
fun ConfirmDeleteDialog(user: User, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Confirm Delete")
        },
        text = {
            Text(text = "Are you sure you want to delete ${user.full_name}?")
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun EmployeeDetailDialog(
    user: User,
    onDismiss: () -> Unit,
    onUpdateRoleClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Employee Details")
        },
        text = {
            Column {
                Text(text = "Full Name: ${user.full_name}")
                Text(text = "Phone Number: ${user.sdt}")
                Text(text = "Email: ${user.email}")
            }
        },
        confirmButton = {
            Row {
                Button(
                    onClick = onUpdateRoleClick, // Gọi callback khi nhấn "Update Role"
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Update Role")
                }
                Button(onClick = onDismiss) {
                    Text("Close")
                }
            }
        }
    )
}

@Composable
fun UpdateRoleDialog(
    user: User,
    onDismiss: () -> Unit,
    onUpdateRole: (String) -> Unit // Callback nhận vai trò mới
) {
    var selectedRole by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Update Role for ${user.full_name}")
        },
        text = {
            Column {
                Text("Current Role: ${user.role}")
                OutlinedTextField(
                    value = selectedRole,
                    onValueChange = { selectedRole = it },
                    label = { Text("New Role") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onUpdateRole(selectedRole) // Gửi vai trò mới khi nhấn "Confirm"
                    onDismiss()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


