package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.items
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.MenuManagementScreen
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component.CategoryRow
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component.MenuItemCard
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component.sampleItems
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.CategoryProduct
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.ItemOrderProduct
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.TopBarComponent
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.ListData

@Composable
fun HomeWaiterScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("Tất cả") }


    Scaffold(
        topBar = {
            // Gọi TopBarComponent với nội dung tùy chỉnh
            TopBarComponent(
                title = "Quản lý nhà hàng",
                showNavigationIcon = true,
                onNavigationClick = {
                    navController.popBackStack()
                },
                navigationIcon = {
                    null
//                    IconButton(onClick = { /* Custom action */ }) {
//                        Icon(
//                            imageVector = Icons.Default.Menu,
//                            contentDescription = "Menu"
//                        )
//                    }
                },
                actions = {
                    IconButton(onClick = { /* Custom action */ }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color(0xFFFFFFFF)
                        )
                    }
                }
            )

        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
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
                        .padding(horizontal = 3.dp)
                )

                Spacer(modifier = Modifier.height(5.dp))


                CategoryProduct(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it })

                Spacer(modifier = Modifier.height(5.dp))

                val filteredItems = ListData.filter { item ->
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
                        var quantity by remember { mutableStateOf(0) } // Khởi tạo số lượng mặc định là 0

                        ItemOrderProduct(
                            item = item,
                            quantity = quantity,
                            onIncreaseClick = { quantity++ }, // Tăng số lượng
                            onDecreaseClick = { if (quantity > 0) quantity-- }, // Giảm số lượng
                        )
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
}
