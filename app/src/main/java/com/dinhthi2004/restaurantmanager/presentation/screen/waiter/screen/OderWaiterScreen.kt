package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen

import WaiterTableViewModel
import android.os.Handler
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dinhthi2004.restaurantmanager.model.OrderData
import com.dinhthi2004.restaurantmanager.model.dish.Dish
import com.dinhthi2004.restaurantmanager.model.table.Tabledata
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Oder.OrderItemRow
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Order.FinishedOrder
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.Table.TableDetailDialog
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.dishSampleList
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.orderSampleList
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.tableSampleList
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.WaiterOrderViewModel

@Composable
fun OrderWaiterScreen() {
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Danh sách các tab
    val tabs = listOf("Đang chờ", "Hoàn Thành")

    val waiterTableViewModel: WaiterTableViewModel = viewModel()
    val waiterOrderViewModel: WaiterOrderViewModel = viewModel()

    val tables by waiterTableViewModel.tables.observeAsState(emptyList())
    val bills by waiterOrderViewModel.bills.observeAsState(emptyList())

    var useTables by remember { mutableStateOf(tables) }
    var emptyTables by remember { mutableStateOf(tables) }
    var bookingTables by remember { mutableStateOf(tables) }

    if (tables.isNotEmpty()){
        useTables = tables.filter { it.status == "Occupied" }
        emptyTables = tables.filter {it.status == "Available"}
        bookingTables = tables.filter { it.status == "Booked" }
    }

    LaunchedEffect(Unit) {
        waiterOrderViewModel.getBills()
        waiterTableViewModel.getTables()
    }

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
                tables = useTables,
                onCompleteTable = {
//                    var updateTable = it
                    it.status = "Available"
//                    println(it)
                    waiterTableViewModel.updateTable(it.id.toString(), it)
                    Handler().postDelayed({
                        waiterTableViewModel.getTables()
                    },300)
                }
            )
            1 -> CompletedTablesTab(emptyTables)
        }
    }
}

@Composable
fun WaitingTablesTab(
    tables: List<Tabledata>, // Sử dụng Tabledata
    onCompleteTable: (Tabledata) -> Unit,
) {
    var selectedTable by remember { mutableStateOf<Tabledata?>(null) }
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
        println(selectedTable)
        TableDetailDialog(
            table = selectedTable!!,
            onDismiss = { showDialog = false }
        )
    }
}

// Cập nhật hàm lấy danh sách order của bàn
fun getOrdersForTable(tableId: Int): MutableList<Pair<OrderData, Dish>> {
    if (tableId == -1) return mutableListOf() // Trả về danh sách rỗng nếu tableId không hợp lệ
    return orderSampleList.filter { it.table_id == tableId }.mapNotNull { orderData ->
        val dish = dishSampleList.find { it.id == orderData.dish_id }
        if (dish != null) {
            orderData to dish
        } else {
            null
        }
    }.toMutableList()
}

@Composable
fun CompletedTablesTab(completedTables: List<Tabledata>) {
    var selectedTable by remember { mutableStateOf<Tabledata?>(null) }
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
