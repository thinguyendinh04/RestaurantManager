package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dinhthi2004.restaurantmanager.model.Bill
import com.dinhthi2004.restaurantmanager.model.bill.BillData
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data.HoaDon
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.statusToString


@Composable
fun OrderItem(order: BillData, onOrderSelected: (BillData) -> Unit) {
    val textColor = when (order.status) {
        0 -> Color.Red
        1 -> Color.Green
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .width(170.dp)
            .height(146.dp)
            .padding(start = 10.dp)
            .clickable { onOrderSelected(order) }
            .border(
                border = BorderStroke(1.dp, Color(0xff565E6C)),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Thông tin bàn và tổng giá tiền

            Text(
                text = "Mã đơn hàng: ${order.order_id ?: "Unknown"}",
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = "Total: ${order.tong_tien}k", // Hiển thị tổng tiền hóa đơn
                fontSize = 14.sp,
                color = Color(0xfffe763e)
            )
            // Trạng thái hóa đơn
            Text(
                text = statusToString(order.status),
                fontSize = 12.sp,
                color = textColor
            )
        }
    }
}
