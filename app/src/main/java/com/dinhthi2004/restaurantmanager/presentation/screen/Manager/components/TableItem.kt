package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dinhthi2004.restaurantmanager.model.Table


@Composable
fun TableItem(table: Table, onItemClicked: () -> Unit) {
    // Xác định màu sắc và trạng thái dựa trên table_status
    val statusColor = if (table.table_status == 0) {
        Color.Green // Trống
    } else {
        Color.Red // Đã đặt
    }

    Box(
        modifier = Modifier
            .width(147.dp)
            .height(146.dp)
            .padding(start = 10.dp)
            .clickable { onItemClicked() } // Gọi hàm khi item được nhấp
            .border(
                border = BorderStroke(1.dp, Color(0xff565E6C)),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 10.dp, top = 5.dp)
        ) {
            Text(
                text = "Bàn " + table.table_name,
                fontSize = 14.sp,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Trạng thái: ${if (table.table_status == 0) "Trống" else "Đã đặt"}",
                fontSize = 14.sp,
                color = statusColor, // Thay đổi màu sắc theo trạng thái
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Order: ${table.oder_name}",
                fontSize = 14.sp,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

