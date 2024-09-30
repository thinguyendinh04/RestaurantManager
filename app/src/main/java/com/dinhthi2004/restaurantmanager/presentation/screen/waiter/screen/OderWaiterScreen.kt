package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen

import android.R
import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.waitingOrders
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.Order

@Composable
fun OrderWaiterScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Danh sách các tab
    val tabs = listOf("Đang chờ", "Hoàn Thành", "Đã Hủy")

    // Màu sắc cho từng tab
    val tabColors = listOf(
        Color(0xFFFF5000), // Màu cam cho tab Đang chờ
        Color(0xFFFF5000), // Màu xanh cho tab Hoàn Thành
        Color(0xFFFF5000)  // Màu đỏ cho tab Đã Hủy
    )

    // Màn hình chính
    Column(modifier = Modifier.fillMaxSize()) {
        // TabRow để hiển thị các tab
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Gray // Màu nền cho TabRow
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) tabColors[index] else Color.White // Màu chữ
                        )
                    },
                    modifier = Modifier.padding(bottom = if (selectedTabIndex == index) 2.dp else 0.dp) // Gạch dưới
                )
            }
        }

        // Nội dung của từng tab
        when (selectedTabIndex) {
            0 -> WaitingOrdersTab() // Tab Đang chờ
            1 -> CompletedOrdersTab() // Tab Hoàn Thành
            2 -> CanceledOrdersTab() // Tab Đã Hủy
        }
    }
}

@Composable
fun WaitingOrdersTab() {
    var selectedOrder by remember { mutableStateOf<Order?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(waitingOrders) { order ->
            OrderItemRow(order = order,
                onClick = {
                    selectedOrder = order
                    showDialog = true
                },
                onCancel = {
                    // Xử lý logic Hủy
                },
                onComplete = {
                    // Xử lý logic Hoàn thành
                }
            )
        }
    }

    // Hiển thị dialog nếu showDialog là true
    if (showDialog && selectedOrder != null) {
        OrderDetailDialog(order = selectedOrder!!) {
            showDialog = false
        }
    }
}
@Composable
fun CompletedOrdersTab() {
    // Hiển thị nội dung cho tab Hoàn Thành
    Text("Nội dung Hoàn Thành", modifier = Modifier.padding(16.dp))
}

@Composable
fun CanceledOrdersTab() {
    // Hiển thị nội dung cho tab Đã Hủy
    Text("Nội dung Đã Hủy", modifier = Modifier.padding(16.dp))
}
@Composable
fun OrderDetailDialog(order: Order, onDismiss: () -> Unit) {
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
                            Text(item.name, modifier = Modifier.weight(1f))
                            Text("SL: ${item.quantity}", modifier = Modifier.weight(1f))
                            Text("${item.price} VNĐ", modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Đóng")
            }
        }
    )
}
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

            // Hiển thị danh sách món ăn
            Column(modifier = Modifier.fillMaxWidth()) {
                order.items.forEach { item ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(item.name, modifier = Modifier.weight(2f)) // Tên món chiếm 2 phần
                        Text("SL: ${item.quantity}", modifier = Modifier.weight(0.5f)) // Số lượng chiếm 0.5 phần
                        Text(" ", modifier = Modifier.weight(0.2f))
                        Text("${item.price} VNĐ", modifier = Modifier.weight(1.3f)) // Giá tiền chiếm 1.5 phần
                    }
                }
            }
            Spacer(modifier = Modifier.height((8.dp)))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Tổng tiền:", style = MaterialTheme.typography.titleMedium)
                Text("$totalAmount VNĐ", style = MaterialTheme.typography.titleMedium, color=Color(
                    0x0FFF0000
                )
                )
            }

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