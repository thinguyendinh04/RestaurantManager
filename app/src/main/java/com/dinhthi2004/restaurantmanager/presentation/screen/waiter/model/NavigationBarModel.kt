package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*

class NavigationBarModel {
    val barItems = listOf(
        BarItem(
            title = "Trang chủ",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "home"
        ),
        BarItem(
            title = "Đơn hàng",
            selectedIcon = Icons.Filled.LocalGroceryStore,
            unselectedIcon = Icons.Outlined.LocalGroceryStore,
            route = "OderStore",
            badgeCount = "14+"
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