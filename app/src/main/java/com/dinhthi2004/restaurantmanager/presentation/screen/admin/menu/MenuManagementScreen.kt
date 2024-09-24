package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component.sampleItems
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component.CategoryRow
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component.MenuItemCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuManagementScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("Tất cả") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Menu Management") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
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
                onClick = {
                    navController.navigate("add_food_screen")
                }
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


            CategoryRow(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it })

            Spacer(modifier = Modifier.height(16.dp))

            val filteredItems = sampleItems.filter { item ->
                (selectedCategory == "Tất cả" || item.category == selectedCategory) &&
                        (searchQuery.isEmpty() || item.name.contains(
                            searchQuery,
                            ignoreCase = true
                        ))
            }

            if (filteredItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Text("No items found", style = MaterialTheme.typography.titleSmall)
                }
            }


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredItems) { item ->
                    MenuItemCard(item, onDeleteClick = {
                        showDialog.value = true
                    })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }


            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "THÔNG BÁO",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color(0xFFFF5000),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    },
                    text = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Xác nhận xóa món ăn này")
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDialog.value = false
                            }
                        ) {
                            Text("Xác nhận")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { showDialog.value = false }
                        ) {
                            Text("Hủy")
                        }
                    }
                )
            }
        }
    }
}
