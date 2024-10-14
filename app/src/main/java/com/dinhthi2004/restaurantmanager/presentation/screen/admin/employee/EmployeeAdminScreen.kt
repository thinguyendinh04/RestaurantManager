package com.dinhthi2004.restaurantmanager.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
    var showUpdateRoleDialog by remember { mutableStateOf(false) }
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
                    showUpdateRoleDialog = true
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
                    viewModel.updateRole(user!!.id, newRole) // Gọi hàm cập nhật vai trò trong ViewModel
                    showUpdateRoleDialog = false // Đóng dialog sau khi cập nhật
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
    onUpdateRole: (Int) -> Unit
) {
    var selectedRole by remember { mutableStateOf(user.role) } // Lưu giữ giá trị số nguyên

    // Danh sách các vai trò
    val roles = listOf("Admin", "Manager", "Waiter")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Update Role for ${user.full_name}", style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Text("Current Role: ${getRoleText(user.role)}", style = MaterialTheme.typography.bodyMedium)

                roles.forEachIndexed { index, role ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedRole = index + 1
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (selectedRole == index + 1),
                            onClick = {
                                selectedRole = index + 1
                            }
                        )
                        Text(
                            text = role,
                            modifier = Modifier.padding(start = 8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
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

fun getRoleText(role: Int): String {
    return when (role) {
        1 -> "Admin"
        2 -> "Manager"
        3 -> "Waiter"
        else -> "Unknown"
    }
}
