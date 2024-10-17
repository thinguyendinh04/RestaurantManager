package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu

import MenuItemCard
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.dish.Dish
import com.dinhthi2004.restaurantmanager.presentation.navigation.Routes
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component.DeleteConfirmationDialog
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component.MealDetailDialog

import androidx.compose.material3.CircularProgressIndicator
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuManagementScreen(
    navController: NavController,
    viewModel: MenuManageViewModel = viewModel(),
) {
    val dishList by viewModel.dishList.collectAsState()

    var selectedDish by rememberSaveable { mutableStateOf<Dish?>(null) }
    var dishToDelete by rememberSaveable { mutableStateOf<Dish?>(null) }
    val showDialog = rememberSaveable { mutableStateOf(false) }
    val showDeleteDialog = rememberSaveable { mutableStateOf(false) }

    var searchQuery by remember { mutableStateOf("") }

    val filteredDish = dishList.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    val context = LocalContext.current
    val deleteMealState by viewModel.deleteMealState.collectAsState(null)
    LaunchedEffect(deleteMealState) {
        deleteMealState?.let {
            it.onSuccess { message ->
                Log.d("MenuManagement", message)
                viewModel.getAllDishes()
                Toast.makeText(context, "Xóa món ăn thành công", Toast.LENGTH_SHORT).show()
            }
            it.onFailure { error ->
                Log.e("MenuManagement", "Error: ${error.localizedMessage}")
                Toast.makeText(context, "Xóa món ăn thất bại", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAllDishes()
    }

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

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
                    navController.navigate(Routes.ADD_NEW_FOOD)
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

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    viewModel.getAllDishes()
                }
            ) {
                if (filteredDish.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Text("No items found", style = MaterialTheme.typography.titleSmall)
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(filteredDish) { dish ->
                            MenuItemCard(
                                dish = dish,
                                onClick = {
                                    selectedDish = dish
                                    showDialog.value = true
                                },
                                onDeleteClick = {
                                    dishToDelete = dish
                                    showDeleteDialog.value = true
                                },
                                onUpdateClick = {
                                    navController.navigate("update_food?dishId=${dish.id}")
                                }

                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            if (showDialog.value && selectedDish != null) {
                MealDetailDialog(
                    dish = selectedDish!!,
                    onDismiss = { showDialog.value = false }
                )
            }

            if (showDeleteDialog.value && dishToDelete != null) {
                DeleteConfirmationDialog(
                    dish = dishToDelete!!,
                    onConfirm = {
                        dishToDelete!!.id.let { viewModel.deleteDish(id = it.toString()) }
                        showDeleteDialog.value = false
                    },
                    onDismiss = {
                        showDeleteDialog.value = false
                    }
                )
            }
        }
    }
}

