package com.dinhthi2004.restaurantmanager.presentation.screen.admin.employee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeScreen(
    navController: NavController,

) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Employee Management") },
                navigationIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Item"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search Icon"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(employees) { employee ->
                    EmployeeCard(
                        name = employee.name,
                        phoneNumber = employee.phoneNumber,
                        shift = employee.shift,
                        role = employee.role,
                        avatarResId = employee.avatarResId,
                        onEditClick = {

                        },
                        onDeleteClick = {

                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EmployeeCard(
    name: String,
    phoneNumber: String,
    shift: String,
    role: String,
    avatarResId: Int,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = avatarResId),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Employee Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Tên: $name", style = MaterialTheme.typography.titleSmall)
                Text(text = "SĐT: $phoneNumber", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Ca làm việc: $shift", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Chức vụ: $role", style = MaterialTheme.typography.bodyMedium)
            }

            IconButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }

            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

data class Employee(
    val id: Int,
    val name: String,
    val phoneNumber: String,
    val shift: String,
    val role: String,
    val avatarResId: Int
)

val employees = listOf(
    Employee(
        1,
        "Nguyễn Đình Thi",
        "09xxxxxxxx",
        "11h-21h",
        "Nhân viên",
        R.drawable.ic_launcher_background
    ),
    Employee(
        2,
        "Nguyễn Đình Thi",
        "09xxxxxxxx",
        "11h-21h",
        "Nhân viên",
        R.drawable.ic_launcher_foreground
    ),
    Employee(
        3,
        "Nguyễn Đình Thi",
        "09xxxxxxxx",
        "11h-21h",
        "Nhân viên",
        R.drawable.ic_launcher_foreground
    ),
    Employee(
        4,
        "Nguyễn Đình Thi",
        "09xxxxxxxx",
        "11h-21h",
        "Nhân viên",
        R.drawable.ic_launcher_foreground
    )
)