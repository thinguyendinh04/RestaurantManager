package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Oder.OrderItemRow
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Order.FinishedOrderItem
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.OrderDetailDialog
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.waitingOrders
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.Order

@Composable
fun OrderWaiterScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Danh sách các tab
    val tabs = listOf("Đang chờ", "Hoàn Thành", "Đã Hủy")

    // Dữ liệu đơn hàng
    var waitingOrders by remember { mutableStateOf(waitingOrders.toMutableList()) }
    var completedOrders by remember { mutableStateOf(listOf<Order>()) }
    var canceledOrders by remember { mutableStateOf(listOf<Order>()) }

    // Màn hình chính
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Gray
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color.Green else Color.White
                        )
                    },
                    modifier = Modifier.padding(bottom = if (selectedTabIndex == index) 2.dp else 0.dp)
                )
            }
        }

        when (selectedTabIndex) {
            0 -> WaitingOrdersTab(
                orders = waitingOrders,
                onCompleteOrder = { completedOrder ->
                    waitingOrders = waitingOrders.filter { it != completedOrder }.toMutableList()
                    completedOrders = completedOrders + completedOrder
                    selectedTabIndex = 1 // Chuyển sang tab Hoàn Thành
                },
                onCancelOrder = { canceledOrder ->
                    waitingOrders = waitingOrders.filter { it != canceledOrder }.toMutableList()
                    canceledOrders = canceledOrders + canceledOrder
                    selectedTabIndex = 2 // Chuyển sang tab Đã Hủy
                }
            )

            1 -> CompletedOrdersTab(completedOrders)
            2 -> CanceledOrdersTab(canceledOrders)
        }
    }
}

@Composable
fun WaitingOrdersTab(
    orders: List<Order>,
    onCompleteOrder: (Order) -> Unit,
    onCancelOrder: (Order) -> Unit
) {
    var selectedOrder by remember { mutableStateOf<Order?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(orders) { order ->
            OrderItemRow(order = order,
                onClick = {
                    selectedOrder = order
                    showDialog = true
                },
                onCancel = {
                    onCancelOrder(order)
                },
                onComplete = {
                    onCompleteOrder(order)
                }
            )
        }
    }

    // Hiển thị dialog nếu showDialog là true
    if (showDialog && selectedOrder != null) {
        OrderDetailDialog(order = selectedOrder!!, onDismiss = { showDialog = false })
    }
}

@Composable
fun CompletedOrdersTab(completedOrders: List<Order>) {
    var selectedOrder by remember { mutableStateOf<Order?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(completedOrders) { order ->
            FinishedOrderItem(
                order = order,
                onClick = {
                    selectedOrder = order
                    showDialog = true
                }
            )
        }
    }
    // Hiển thị dialog nếu showDialog là true
    if (showDialog && selectedOrder != null) {
        OrderDetailDialog(order = selectedOrder!!, onDismiss = { showDialog = false })
    }
}

@Composable
fun CanceledOrdersTab(canceledOrders: List<Order>) {
    var selectedOrder by remember { mutableStateOf<Order?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(canceledOrders) { order ->
            FinishedOrderItem(
                order = order,
                onClick = {
                    selectedOrder = order
                    showDialog = true
                }
            )
        }
    }
    // Hiển thị dialog nếu showDialog là true
    if (showDialog && selectedOrder != null) {
        OrderDetailDialog(order = selectedOrder!!, onDismiss = { showDialog = false })
    }
}



