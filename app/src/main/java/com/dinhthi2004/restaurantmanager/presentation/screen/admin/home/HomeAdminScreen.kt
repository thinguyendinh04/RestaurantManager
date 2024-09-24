package com.dinhthi2004.restaurantmanager.presentation.screen.admin.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.navigation.bottomnav.BottomBar
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.home.component.HomeHeader
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.home.component.MenuManagement
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.home.component.RevenueReports
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.home.component.TextFieldSearch

@Composable
fun HomeAdminScreen(navController: NavController) {

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            HomeHeader()
            TextFieldSearch()
            RevenueReports()
            MenuManagement(navController)
        }
    }
}

