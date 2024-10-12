package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.data.Employee
import com.dinhthi2004.restaurantmanager.data.employ
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.EmployeeCard
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.viewmodel.HomeEmployeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeEmployeeScreen(
    navigationController: NavHostController,
    viewModel: HomeEmployeeViewModel = viewModel()
) {
    val employeeList by viewModel.userList.collectAsState()
    val accountDetail by viewModel.accountDetail.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.getAllUser()
    }

    val filteredEmployeeList = if (searchQuery.isNotBlank()) {
        employeeList.filter { it.username.contains(searchQuery, ignoreCase = true) }
    } else {
        employeeList
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                modifier = Modifier
                    .height(500.dp)
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                items(filteredEmployeeList) { index ->
                    EmployeeCard(employee= index) {}
                }
            }
        }
    }
}


