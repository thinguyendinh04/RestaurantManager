package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Order.FinishedOrder
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.Table
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.Table.TableDetailDialog

import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.dataTables

@Composable
fun OrderWaiterScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Danh sách các tab
    val tabs = listOf("Đang chờ", "Hoàn Thành")

    // Dữ liệu bàn
    var waitingTables by remember { mutableStateOf(dataTables.filter { it.status == "Occupied" }) }
    var completedTables by remember { mutableStateOf(dataTables.filter { it.status == "Paid" }) }

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
            0 -> WaitingTablesTab(
                tables = waitingTables,
                onCompleteTable = { completedTable ->
                    waitingTables = waitingTables.filter { it != completedTable }.toMutableList()
                    completedTables = completedTables + completedTable
                }
            )
            1 -> CompletedTablesTab(completedTables)
        }
    }
}

@Composable
fun WaitingTablesTab(
    tables: List<Table>,
    onCompleteTable: (Table) -> Unit,
) {
    var selectedTable by remember { mutableStateOf<Table?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(tables) { table ->
            OrderItemRow(table = table,
                onClick = {
                    selectedTable = table
                    showDialog = true
                },
                onComplete = {
                    onCompleteTable(table)
                }
            )
        }
    }

    // Hiển thị dialog nếu showDialog là true
    if (showDialog && selectedTable != null) {
        TableDetailDialog(
            table = selectedTable!!, // Bàn được chọn
            orderItems = selectedTable!!.orders, // Danh sách order của bàn
            onAddItem = { /* Hàm thêm món ở đây nếu cần */ },
            onDismiss = {
                showDialog = false // Đóng dialog khi nhấn "Đóng"
            }
        )
    }
}

@Composable
fun CompletedTablesTab(completedTables: List<Table>) {
    var selectedTable by remember { mutableStateOf<Table?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(completedTables) { table ->
            FinishedOrder(
                table = table,
            )
        }
    }

    // Hiển thị dialog nếu showDialog là true
    if (showDialog && selectedTable != null) {
        // Hiển thị chi tiết của table
        FinishedOrder(table = selectedTable!!)
    }
}




