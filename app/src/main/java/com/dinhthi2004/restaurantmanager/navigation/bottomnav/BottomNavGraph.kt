package com.dinhthi2004.restaurantmanager.navigation.bottomnav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.employee.EmployeeScreen
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.home.HomeAdminScreen
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.restaurant_management.RestaurantManagerScreen
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.revenue_report.RevenueReportScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route
    ) {
        composable(route = BottomNavItem.Home.route) {
            HomeAdminScreen(navController)
        }
        composable(route = BottomNavItem.Employee.route) {
            EmployeeScreen(navController)
        }
        composable(route = BottomNavItem.RevenueReports.route) {
            RevenueReportScreen(navController)
        }
        composable(route = BottomNavItem.ManagerRestaurant.route) {
            RestaurantManagerScreen(navController)
        }
    }
}