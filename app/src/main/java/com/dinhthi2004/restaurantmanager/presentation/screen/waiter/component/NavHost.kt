package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component

import TableWaiterScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.*


@Composable
fun NavHostComponent(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeWaiterScreen(navController) // Điều hướng tới HomeWaiterScreen
        }
        composable("OderStore") {
            OrderWaiterScreen(navController) // Điều hướng tới OderWaiterScreen
        }
        composable("table") {
            TableWaiterScreen(navController) // Điều hướng tới TableWaiterScreen
        }
        composable("setting") {
            SetingWaiterScreen(navController) // Điều hướng tới SettingWaiterScreen
        }
    }
}
