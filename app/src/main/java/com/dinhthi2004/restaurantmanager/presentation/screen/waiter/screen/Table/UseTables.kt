package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.Table

import WaiterTableViewModel
import android.os.Handler
import android.util.MutableFloat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dinhthi2004.restaurantmanager.model.Order
import com.dinhthi2004.restaurantmanager.model.OrderData
import com.dinhthi2004.restaurantmanager.model.dish.Dish
import com.dinhthi2004.restaurantmanager.model.table.Tabledata
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Invoice
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.OrderItem
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.dishSampleList
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.orderSampleList
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.tableSampleList
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.WaiterHomeViewModel
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.WaiterOrderViewModel
import java.text.DecimalFormat

@Composable
fun InUseTables(
    tables: List<Tabledata>,
    onTableUpdate: (List<Tabledata>) -> Unit,
    onEmptyTableUpdate: (List<Tabledata>) -> Unit,
) {
    var selectedTable by remember { mutableStateOf<Tabledata?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var showPaymentDialog by remember { mutableStateOf(false) }

    val waiterTableViewModel: WaiterTableViewModel = viewModel()

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(tables) { table ->
            TableItemRow(
                table = table,
                onClickDetail = {
                    selectedTable = table
                    showDialog = true
                },
                onClickPay = {
                    selectedTable = table
                    showPaymentDialog = true
                }
            )
        }
    }

    if (showDialog && selectedTable != null) {
        TableDetailDialog(
            table = selectedTable!!,
            onDismiss = { showDialog = false }
        )
    }

    if (showPaymentDialog && selectedTable != null) {
        ConfirmPaymentDialog(
            table = selectedTable!!, // Truyền mã bàn hoặc hóa đơn
            onConfirm = { table ->
                table.status = "Available"
                waiterTableViewModel.updateTable(table.id!!.toString(), table)
                waiterTableViewModel.getTables()
                showPaymentDialog = false // Đóng dialog sau khi xác nhận
            },
            onCancel = {
                showPaymentDialog = false // Đóng dialog khi hủy
            }
        )
    }
}

@Composable
fun TableItemRow(table: Tabledata, onClickDetail: () -> Unit, onClickPay: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Hiển thị tên bàn và chi tiết khách hàng
            Text(
                text = "Bàn ${table.table_name}",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Khách: ${table.customer_name}",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nút Chi tiết và Thanh toán
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onClickDetail,
                    border = BorderStroke(1.dp, Color.Red),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .height(36.dp)
                ) {
                    Text(
                        text = "Chi tiết",
                        color = Color.Red,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Button(
                    onClick = onClickPay,
                    colors = ButtonDefaults.buttonColors(Color(0xFFE0FFE0)),
                    modifier = Modifier.height(36.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(text = "Thanh toán", color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun TableDetailDialog(
    table: Tabledata,
    onDismiss: () -> Unit,
) {
    var totalPrice by remember { mutableFloatStateOf(0f) }

    var showAddItemsDialog by remember { mutableStateOf(false) }

    val waiterTableViewModel: WaiterTableViewModel = viewModel()
    val waiterHomeViewModel: WaiterHomeViewModel = viewModel()
    val waiterOrderViewModel: WaiterOrderViewModel = viewModel()

    val tableOrders by waiterTableViewModel.tableOrders.observeAsState(emptyList())
    val meals by waiterHomeViewModel.meals.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        waiterTableViewModel.getOrdersByTableID(table.id!!)
        waiterHomeViewModel.getMeals()
    }

    if(tableOrders == null || meals.isNotEmpty()){
        waiterTableViewModel.getOrdersByTableID(table.id!!)
        waiterHomeViewModel.getMeals()
    }

    LaunchedEffect(tableOrders, meals) {
        if(tableOrders != null && meals.isNotEmpty()){
            for (tableOrder in tableOrders!!){
                totalPrice += (meals.find { it.id == tableOrder.dish_id }!!.price.toFloat() * tableOrder.amount)
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Chi tiết bàn ${table.table_name}",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color(0xFFFF6D00),
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 8.dp),
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Bàn ${table.table_name} - ${table.customer_name}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Danh sách order:",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )

                // Hiển thị danh sách các món ăn đã order
                LazyColumn(modifier = Modifier.weight(1f).padding(vertical = 8.dp)) {
                    if(meals.isNotEmpty()){
                        items(tableOrders!!) { tableOrder ->
                            val aMeal = meals.find { it.id == tableOrder.dish_id }
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = aMeal!!.name, modifier = Modifier.weight(1f))
                                Text(text = tableOrder.amount.toString(), modifier = Modifier.weight(0.5f))
                                Text(text = "${aMeal.price.toFloat() * tableOrder.amount} đ", modifier = Modifier.weight(1f))
                            }
                            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Tổng cộng:",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "$totalPrice đ",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Đóng", color = Color.Black)
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Nút Thêm món
                Button(
                    onClick = { showAddItemsDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCDD2)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Thêm món", color = Color(0xFFFF6D00))
                }
            }

            // Hiển thị hộp thoại thêm món khi bấm "Thêm món"
            if (showAddItemsDialog) {
                AddItemsDialog(
                    products = waiterHomeViewModel.meals.value!!,
                    onDismiss = { showAddItemsDialog = false },
                    onAddItems = { selectedItems ->
                        for (meal in waiterHomeViewModel.meals.value!!){
                            val amount = selectedItems[meal]
                            if(amount != 0){
                                waiterOrderViewModel.addOrder(Order(table.id!!, meal.id!!, amount!!))
                            }
                        }
                        Handler().postDelayed({
                            waiterTableViewModel.getOrdersByTableID(table.id!!)
                        },300)
                        showAddItemsDialog = false
                    }
                )
            }
        }
    )
}

@Composable
fun ConfirmPaymentDialog(
    table: Tabledata,
    onConfirm: (table: Tabledata) -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(
                text = "THÔNG BÁO",
                style = TextStyle(
                    color = Color(0xFFFF6D00),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        text = {
            Text(
                text = "Xác nhận thanh toán hóa đơn bàn: ${table.table_name}",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF333333)
                )
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(table)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = "Xác nhận", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0)),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = "Hủy", color = Color.Gray)
            }
        }
    )
}
