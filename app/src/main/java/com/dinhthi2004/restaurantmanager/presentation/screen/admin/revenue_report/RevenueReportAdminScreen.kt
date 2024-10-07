package com.dinhthi2004.restaurantmanager.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.navigation.bottomnav.BottomBar
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.revenue_report.RevenueReportViewModel
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.revenue_report.component.DatePickerField
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.revenue_report.component.RevenueRow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevenueReportScreen(
navController: NavController,
    viewModel: RevenueReportViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Thống kê") },
                navigationIcon = {
                    IconButton(onClick = {
//                        navController.navigateUp()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
        },
        bottomBar = {
BottomBar(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DatePickerField(
                label = "Chọn ngày bắt đầu :",
                date = viewModel.startDate,
                onDateSelected = { selectedDate ->
                    viewModel.onStartDateSelected(selectedDate)
                }
            )

            DatePickerField(
                label = "Chọn ngày kết thúc :",
                date = viewModel.endDate,
                onDateSelected = { selectedDate ->
                    viewModel.onEndDateSelected(selectedDate)
                }
            )

            Button(onClick = {
                viewModel.generateReport()
            }) {
                Text(text = "Thống kê")
            }

            viewModel.reportData?.let { data ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(top = 16.dp)
                        .border(1.dp, Color.Black)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("STT", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Ngày", fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f))
                    Text(
                        "Doanh thu (VND)",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(2f)
                    )
                }

                data.revenues.forEachIndexed { index, revenue ->
                    RevenueRow(index + 1, revenue.date, revenue.revenue)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Tổng doanh thu: ${data.totalRevenue}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun Preview(){
//    RevenueReportScreen()
//}