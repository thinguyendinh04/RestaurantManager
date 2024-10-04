package com.dinhthi2004.restaurantmanager.presentation.navigation.bottomnav

import androidx.annotation.DrawableRes
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.navigation.Routes

sealed class BottomNavItem(
    val route: String,
    @DrawableRes val icon: Int,
    val title: String
) {
    object Home : BottomNavItem(
        Routes.HOME_ADMIN_SCREEN,
        R.drawable.ic_home, "Home"
    )

    object Employee : BottomNavItem(
        Routes.EMPLOYEE_ADMIN,
        R.drawable.ic_person, "Employee"
    )

    object RevenueReports : BottomNavItem(
        Routes.REVENUE_REPORTS_ADMIN,
        R.drawable.ic_home, "Revenue"
    )

    object ManagerRestaurant :
        BottomNavItem(
            Routes.RESTAURANT_MANAGER_ADMIN,
            R.drawable.ic_restaurant, "Restaurant"
        )
}
