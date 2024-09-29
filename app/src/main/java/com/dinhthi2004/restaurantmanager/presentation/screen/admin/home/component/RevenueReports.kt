package com.dinhthi2004.restaurantmanager.presentation.screen.admin.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.navigation.Routes

@Composable
fun RevenueReports(
    navController: NavController
) {
    var title by remember { mutableStateOf("Thống kê doanh thu") }
    var money by remember { mutableStateOf("49.000.000 VND") }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        color = Color.White,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicText(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                )
                BasicText(
                    text = "📊",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 32.sp
                    ),
                    modifier = Modifier.clickable {
                        navController.navigate(Routes.REVENUE_REPORTS_ADMIN)
                    }
                )
            }
            BasicText(
                text = money,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 32.sp,
                    color = Color.Blue
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicText(
                    text = "+ 5.39%",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        color = Color.Green
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                BasicText(
                    text = "in 5 month yet",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                )
            }
        }
    }
}