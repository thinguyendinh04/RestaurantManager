package com.dinhthi2004.restaurantmanager.presentation.screen.admin.revenue_report.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RevenueRow(stt: Int, date: String, revenue: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color.Gray)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stt.toString(), modifier = Modifier.weight(1f))
        Text(text = date, modifier = Modifier.weight(2f))
        Text(text = revenue, modifier = Modifier.weight(2f))
    }
}