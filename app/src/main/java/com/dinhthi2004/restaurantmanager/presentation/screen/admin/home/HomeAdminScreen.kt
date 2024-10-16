package com.dinhthi2004.restaurantmanager.presentation.screen.admin.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.presentation.navigation.bottomnav.BottomBar
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.home.component.HomeHeader
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.home.component.MenuManagement
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.home.component.RevenueReports

@Composable
fun HomeAdminScreen(
    navController: NavController
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {
            HomeHeader()
            RevenueReports(navController)
            MenuManagement(navController)
        }
    }
}

