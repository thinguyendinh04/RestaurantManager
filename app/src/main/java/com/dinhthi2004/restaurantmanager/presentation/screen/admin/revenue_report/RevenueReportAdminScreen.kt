package com.dinhthi2004.restaurantmanager.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.model.bill.BillData
import com.dinhthi2004.restaurantmanager.presentation.navigation.bottomnav.BottomBar
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.OrderItem
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.revenue_report.RevenueReportViewModel
import com.dinhthi2004.restaurantmanager.viewmodel.BillViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevenueReportScreen(
    navController: NavController,
    viewModel: RevenueReportViewModel = viewModel(),
    billViewModel: BillViewModel = viewModel()
) {
    val token = TokenManager.token
    LaunchedEffect(Unit) {
        billViewModel.getBills("Bearer $token")
    }
    val order by billViewModel.bills.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var selectedOrder by remember { mutableStateOf<BillData?>(null) }

    // Tính tổng tiền tất cả hóa đơn bằng vòng for
    val totalRevenue = order.sumOf { it.tong_tien.toDouble() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Thống kê") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = totalRevenue.toString(),
                onValueChange = {},
                label = { Text(text = "Tổng tiền tất cả hóa đơn") },
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(order) { currentOrder ->
                    OrderItem(order = currentOrder) { selected ->
                        selectedOrder = selected
                        showDialog = true
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewRevenue() {
    RevenueReportScreen(navController = rememberNavController())
}
