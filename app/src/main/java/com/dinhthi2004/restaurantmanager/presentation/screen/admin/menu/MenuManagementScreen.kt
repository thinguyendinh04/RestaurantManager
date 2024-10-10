package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.Meal
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component.MenuItemCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuManagementScreen(
    navController: NavController,
    viewModel: MenuManageViewModel = viewModel()
) {
    val mealList by viewModel.mealList.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredItems = viewModel.filteredItems(mealList)

    var selectedMeal by remember { mutableStateOf<Meal?>(null) } // Món ăn được chọn
    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAllMeals()
    }

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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
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

            if (filteredItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Text("No items found", style = MaterialTheme.typography.titleSmall)
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredItems) { meal ->
                        MenuItemCard(
                            meal = meal,
                            onClick = {
                                selectedMeal = meal
                                showDialog.value = true // Hiển thị dialog
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            // Hiển thị dialog khi món ăn được chọn
            if (showDialog.value && selectedMeal != null) {
                MealDetailDialog(
                    meal = selectedMeal!!,
                    onDismiss = { showDialog.value = false }
                )
            }
        }
    }
}

@Composable
fun MealDetailDialog(meal: Meal, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Chi tiết món ăn", style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Column {
                Text(text = "Tên: ${meal.name}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Giá: ${meal.price} VND", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Trạng thái: ${if (meal.status == 1) "Còn hàng" else "Hết hàng"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = "Thông tin: ${meal.info}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Đánh giá: ${meal.rating}", style = MaterialTheme.typography.bodyMedium)
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Đóng")
            }
        }
    )
}



