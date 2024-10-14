package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.Table

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Invoice
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Table.ItemOrderProduct
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.ListData
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.OrderItem
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.dataProduct
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.dataTables
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.Table
import java.text.DecimalFormat

@Composable
fun InUseTables(
    tables: List<Table>,
    onTableUpdate: (List<Table>) -> Unit,
    onEmptyTableUpdate: (List<Table>) -> Unit,
    invoices: SnapshotStateList<Invoice>
) {
    var selectedTable by remember { mutableStateOf<Table?>(null) } // Bàn được chọn
    var showDialog by remember { mutableStateOf(false) } // Kiểm soát việc hiển thị dialog
    var showPaymentDialog by remember { mutableStateOf(false) } // Kiểm soát dialog thanh toán

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(tables) { table ->
            TableItemRow(
                table = table,
                onClickDetail = {
                    selectedTable = table // Cập nhật bàn được chọn
                    showDialog = true // Mở dialog chi tiết bàn
                },
                onClickPay = {
                    selectedTable = table // Cập nhật bàn được chọn
                    showPaymentDialog = true // Mở dialog xác nhận thanh toán
                }
            )
        }
    }

    // Hiển thị dialog chi tiết bàn nếu có bàn được chọn và showDialog == true
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

    // Hiển thị dialog xác nhận thanh toán nếu có bàn được chọn và showPaymentDialog == true
    if (showPaymentDialog && selectedTable != null) {
        ConfirmPaymentDialog(
            tableId = selectedTable!!.tableName, // Truyền mã bàn hoặc hóa đơn
            onConfirm = {
                // Cập nhật trạng thái bàn thành "Empty" và xóa đơn hàng
                selectedTable?.let { table ->
                    val updatedTable = table.copy(status = "Empty", orders = emptyList()) // Xóa order của bàn
                    val updatedTables = tables.filter { it.tableName != table.tableName } // Xóa bàn khỏi danh sách bàn đang sử dụng
                    onTableUpdate(updatedTables) // Cập nhật lại danh sách bàn đang sử dụng

                    // Thêm bàn vừa thanh toán vào danh sách bàn trống
                    onEmptyTableUpdate(dataTables.filter { it.status == "Empty" } + updatedTable)
                }

                showPaymentDialog = false // Đóng dialog sau khi xác nhận
            },
            onCancel = {
                showPaymentDialog = false // Đóng dialog khi hủy
            }
        )
    }
}
@Composable
fun TableItemRow(table: Table, onClickDetail: () -> Unit, onClickPay: () -> Unit) {
    // Tính tổng tiền từ danh sách orderItems
    val totalAmount = table.orders.sumOf { it.price * it.quantity }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp), // Góc bo cho Card
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Tên bàn
            Text(
                text = "Bàn ${table.tableName}",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Vị trí bàn
            Text(
                text = "Vị trí: ${table.location}",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            )

            // Số khách hiện tại
            Text(
                text = "Số lượng: ${table.currentGuests}",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            )

            // Tổng tiền
            Text(
                text = "Tổng tiền: ${totalAmount} đ",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Nút Chi tiết
                OutlinedButton(
                    onClick = onClickDetail,
                    border = BorderStroke(1.dp, Color.Red),
                    shape = RoundedCornerShape(50), // Bo góc cho nút
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .height(36.dp) // Đảm bảo chiều cao
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

                // Nút Thanh toán
                Button(
                    onClick = onClickPay,
                    colors = ButtonDefaults.buttonColors(Color(0xFFE0FFE0)),
                    modifier = Modifier.height(36.dp), // Đảm bảo chiều cao
                    shape = RoundedCornerShape(50) // Bo góc cho nút
                ) {
                    Text(text = "Thanh toán", color = Color.Black)
                }
            }
        }
    }
}


@Composable
fun TableDetailDialog(
    table: Table,
    orderItems: List<OrderItem>, // List of orders
    onAddItem: (List<OrderItem>) -> Unit, // Hàm để thêm sản phẩm vào danh sách order
    onDismiss: () -> Unit
) {
    val maxItemsToShow = 5
    val displayedOrderItems =
        if (orderItems.size > maxItemsToShow) orderItems.take(maxItemsToShow) else orderItems
    val totalPrice = orderItems.sumOf { it.price * it.quantity }
    val decimalFormat = DecimalFormat("#,###.00")
    // Biến trạng thái để quản lý việc hiển thị AddItemsDialog
    var showAddItemsDialog by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Chi tiết bàn ${table.tableName}",
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
                    text = "Bàn ${table.tableName}  ${table.location}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(text = "Số lượng: ${table.currentGuests}")
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Danh sách order:",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Món",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "SL",
                        modifier = Modifier.weight(0.5f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Giá",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Divider(color = Color.Gray, thickness = 1.dp)

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                ) {
                    items(orderItems) { orderItem ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = orderItem.name, modifier = Modifier.weight(1f))
                            Text(
                                text = orderItem.quantity.toString(),
                                modifier = Modifier.weight(0.5f)
                            )
                            Text(text = "${orderItem.price} đ", modifier = Modifier.weight(1f))
                        }

                        Divider(color = Color.LightGray, thickness = 0.5.dp)
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
                        text = "${decimalFormat.format(totalPrice)} đ",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
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

                Button(
                    onClick = {
                        showAddItemsDialog = true // Hiển thị AddItemsDialog khi nhấn "Thêm món"
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCDD2)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Thêm món", color = Color(0xFFFF6D00))
                }
            }

            if (showAddItemsDialog) {
                AddItemsDialog(
                    products = ListData, // Truyền danh sách sản phẩm
                    onDismiss = { showAddItemsDialog = false },
                    onAddItems = { selectedItems ->
                        val newOrderItems = selectedItems.map { (product, quantity) ->
                            OrderItem(
                                name = product.name,
                                quantity = quantity,
                                price = product.price.toDoubleOrNull() ?: 0.0

                            )
                        }
                        onAddItem(newOrderItems) // Cập nhật danh sách orderItems
                        showAddItemsDialog = false
                    }
                )
            }
        }
    )
}

@Composable
fun ConfirmPaymentDialog(
    tableId: String, // Mã đơn hàng cần hiển thị
    onConfirm: () -> Unit, // Hành động khi xác nhận
    onCancel: () -> Unit // Hành động khi hủy
) {
    AlertDialog(
        onDismissRequest = onCancel, // Khi nhấn ngoài hộp thoại hoặc nhấn nút "Hủy"
        title = {
            Text(
                text = "THÔNG BÁO",
                style = TextStyle(
                    color = Color(0xFFFF6D00), // Màu chữ cam đậm giống hình ảnh
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        text = {
            Text(
                text = "Xác nhận thanh toán hóa  đơn bàn:  $tableId",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF333333) // Màu chữ xám đậm
                )
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm, // Xác nhận thanh toán
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)), // Màu xanh giống hình
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = "Xác nhận", color = Color.White) // Nút "Xác nhận" màu xanh lá cây
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel, // Đóng thông báo
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0)), // Nút màu xám
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = "Hủy", color = Color.Gray) // Nút "Hủy" màu xám
            }
        }
    )
}
@Composable
fun InvoiceList(invoices: List<Invoice>) {
    if (invoices.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Không có hóa đơn nào.")
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(invoices) { invoice ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Hóa đơn #${invoice.invoiceId}",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333)
                            )
                        )
                        Text(
                            text = "Bàn: ${invoice.tableName}",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF666666)
                            )
                        )
                        Text(
                            text = "Tổng tiền: ${invoice.totalAmount} đ",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF00C853)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Danh sách món:",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF6D00)
                            )
                        )
                        invoice.orderList.forEach { orderItem ->
                            Text(
                                text = "${orderItem.name} x${orderItem.quantity} - ${orderItem.price} đ",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}