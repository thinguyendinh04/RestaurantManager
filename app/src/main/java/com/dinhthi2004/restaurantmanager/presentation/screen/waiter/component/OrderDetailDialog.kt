package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.Order

@Composable
fun OrderDetailDialog(order: Order, onDismiss: () -> Unit) {
    val totalAmount = order.items.sumOf { it.price.toDouble() * it.quantity }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Chi tiết đơn hàng") },
        text = {
            Column {
                Text("Mã đơn hàng: ${order.orderId}", style = MaterialTheme.typography.titleMedium)
                Text("Thời gian: ${order.time}", style = MaterialTheme.typography.bodyMedium)
                Text("Người nhận: ${order.recipient}", style = MaterialTheme.typography.bodyMedium)
                Text("Địa chỉ: ${order.address}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))

                Text("Danh sách món ăn:", style = MaterialTheme.typography.titleMedium)

                // Hiển thị danh sách món ăn
                Column(modifier = Modifier.fillMaxWidth()) {
                    order.items.forEach { item ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(item.name, modifier = Modifier.weight(2f))
                            Text("SL: ${item.quantity}", modifier = Modifier.weight(0.5f))
                            Text("${item.price} VNĐ", modifier = Modifier.weight(1.5f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Hiển thị tổng tiền
                Text("Tổng tiền: $totalAmount VNĐ", style = MaterialTheme.typography.titleMedium)
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Đóng")
            }
        }
    )
}