package com.dinhthi2004.restaurantmanager.presentation.screen.admin.restaurant_management

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.navigation.Routes
import com.dinhthi2004.restaurantmanager.presentation.navigation.bottomnav.BottomBar
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.restaurant_management.component.OptionItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantManagerScreen(
    navController: NavController,
    viewModel: RestaurantManagerViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Restaurant Management") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Restaurant Icon",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.size(16.dp))

            OptionItem(
                iconRes = R.drawable.logo,
                title = "Menu Management",
                onClick = {
                    navController.navigate(Routes.MENU_MANAGEMENT_ADMIN)
                }
            )

            OptionItem(
                iconRes = R.drawable.ic_revenue,
                title = "Doanh thu",
                onClick = {
                    navController.navigate(Routes.REVENUE_REPORTS_ADMIN)
                }
            )

            OptionItem(
                iconRes = R.drawable.ic_setting,
                title = "Role and Permission",
                onClick = {
                    navController.navigate(Routes.EMPLOYEE_ADMIN)
                }
            )

            OptionItem(
                iconRes = R.drawable.ic_logout,
                title = "Logout",
                onClick = {
                    navController.navigate(Routes.LOGIN_SCREEN)
                }
            )
        }
    }
}
