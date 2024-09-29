package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.HomeWaiterScreen
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.OderWaiterScreen
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.SettingWaiterScreen
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.TableWaiterScreen

@Composable
fun NavHostComponent(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeWaiterScreen(navController) // Điều hướng tới HomeWaiterScreen
        }
        composable("OderStore") {
            OderWaiterScreen(navController) // Điều hướng tới OderWaiterScreen
        }
        composable("table") {
            TableWaiterScreen(navController) // Điều hướng tới TableWaiterScreen
        }
        composable("setting") {
            SettingWaiterScreen(navController) // Điều hướng tới SettingWaiterScreen
        }
    }
}
