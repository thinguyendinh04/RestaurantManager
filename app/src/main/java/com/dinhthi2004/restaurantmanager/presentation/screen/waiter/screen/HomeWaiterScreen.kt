package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.Meal
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.CategoryProduct
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.ItemOrderProduct
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.TopBarComponent
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.ListData
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.WaiterHomeViewModel

@Composable
fun HomeWaiterScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Tất cả") }

    val waiterHomeViewModel: WaiterHomeViewModel = viewModel()
    waiterHomeViewModel.getMeals()
    val meals by waiterHomeViewModel.meals.observeAsState(emptyArray<Meal>().toList())

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBarComponent(
            title = "Menu Management",
            showNavigationIcon = true,
            onNavigationClick = {
                navController.popBackStack()
            },
            navigationIcon = {
                null
            },
            actions = {
            }
        )
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

        val filteredItems = ListData.filter { item ->
            (selectedCategory == "Tất cả" || item.category == selectedCategory) && (searchQuery.isEmpty() || item.name.contains(
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


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(meals) { item ->
                var quantity by remember { mutableStateOf(0) }
                ItemOrderProduct(
                    item = item,
                    quantity = quantity,
                    onIncreaseClick = { quantity++ },
                    onDecreaseClick = { if (quantity > 0) quantity-- }, // Giảm số lượng
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun WaiterHomePreview() {
    HomeWaiterScreen(rememberNavController())
}