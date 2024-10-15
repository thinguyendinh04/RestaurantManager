package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Oder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dinhthi2004.restaurantmanager.model.table.Tabledata

@Composable
fun OrderItemRow(table: Tabledata, onClick: () -> Unit, onComplete: () -> Unit) {
//    val totalAmount = table.orders.sumOf { it.price * it.quantity }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Bàn: ${table.table_name}", style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
//            Text("Tổng tiền: ${totalAmount}", style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)

            Spacer(modifier = Modifier.height(16.dp))

            // Nút Chi tiết và Hoàn thành
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    modifier = Modifier.width(120.dp),
                    onClick = onClick,  // Thay thế cho chức năng của nút Chi tiết
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF007BFF) // Màu xanh cho nút Chi tiết
                    )
                ) {
                    Text("Chi tiết")
                }
                Button(
                    modifier = Modifier.width(120.dp),
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
