package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Oder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.Order

@Composable
fun OrderItemRow(order: Order, onClick: () -> Unit, onCancel: () -> Unit, onComplete: () -> Unit) {
    val totalAmount = order.items.sumOf { it.price * it.quantity }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Mã đơn hàng: ${order.orderId}", style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("Thời gian: ${order.time}", style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("Người nhận: ${order.recipient}", style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("Địa chỉ: ${order.address}", style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)

            Spacer(modifier = Modifier.height(8.dp))

            Text("Danh sách món ăn:", style = MaterialTheme.typography.titleMedium)

            // Hiển thị danh sách 3 món ăn đầu tiên
            Column(modifier = Modifier.fillMaxWidth()) {
                order.items.take(3).forEach { item ->  // Giới hạn chỉ hiển thị 3 items
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(item.name, modifier = Modifier.weight(2f))
                        Text("SL: ${item.quantity}", modifier = Modifier.weight(0.5f))
                        Text("${item.price} VNĐ", modifier = Modifier.weight(1.5f))
                    }
                }
            }
            Spacer(modifier = Modifier.height((8.dp)))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Tổng tiền:", style = MaterialTheme.typography.titleMedium)
                Text("$totalAmount VNĐ", style = MaterialTheme.typography.titleMedium, color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nút Hoàn thành và Hủy
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    onClick = onCancel,
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFFFF0000)
                    )
                ) {
                    Text("Hủy")
                }
                Button(
                    onClick = onComplete,
                    colors = ButtonDefaults.buttonColors(
                        Color.Green
                    )
                ) {
                    Text("Hoàn thành")
                }
            }
        }
    }
}
