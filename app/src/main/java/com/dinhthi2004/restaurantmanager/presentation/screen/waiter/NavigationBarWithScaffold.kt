package com.dinhthi2004.restaurantmanager.presentation.screen.waiter

import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.NavigationBarModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.NavHostComponent
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.NavigationBarComponent
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavigationBarWithScaffold() {
    val navController = rememberNavController()
    val model = NavigationBarModel()

    // Observe the current back stack entry to get the current route
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Find the index of the current route in the barItems list
    val selectedItem = model.barItems.indexOfFirst { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            NavigationBarComponent(
                barItems = model.barItems,
                selectedItem = selectedItem,
                onItemSelected = { index ->
                    navController.navigate(model.barItems[index].route) {
                        launchSingleTop = true // Prevent multiple copies of the same screen
                    }
                }
            )
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavHostComponent(navController = navController)
        }
    }
}
