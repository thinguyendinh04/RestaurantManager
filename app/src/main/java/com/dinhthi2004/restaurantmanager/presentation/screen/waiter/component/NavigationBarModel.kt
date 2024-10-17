package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.BarItem

class NavigationBarModel {
    val barItems = listOf(
        BarItem(
            title = "Hoá đơn",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "OrderStore"
        ),
        BarItem(
            title = "Bàn ăn",
            selectedIcon = Icons.Filled.TableRestaurant,
            unselectedIcon = Icons.Outlined.TableRestaurant,
            route = "table"
        ),
        BarItem(
            title = "Cài đặt",
            selectedIcon = Icons.Filled.StackedBarChart,
            unselectedIcon = Icons.Outlined.StackedBarChart,
            route = "setting"
        )
    )
}