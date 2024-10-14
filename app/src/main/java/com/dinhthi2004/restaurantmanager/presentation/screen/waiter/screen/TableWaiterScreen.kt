import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.bookedTables
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.emptyTables
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.occupiedTables
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.Table
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.orderItems
import androidx.compose.material3.*
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.ItemOrderProduct
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.dataProduct

@Composable
fun TableWaiterScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Bàn đang sử dụng", "Bàn trống", "Bàn đặt")

    // Dữ liệu về các bàn
    var useTables by remember { mutableStateOf(occupiedTables.toMutableList()) }
    var emptyTables by remember { mutableStateOf(emptyTables.toMutableList()) }
    var bookingTables by remember { mutableStateOf(bookedTables.toMutableList()) }

    Column(modifier = Modifier.fillMaxSize()) {
        // TabRow để điều hướng giữa các loại bàn
        androidx.compose.material3.TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Gray
        ) {
            tabs.forEachIndexed { index, title ->
                androidx.compose.material3.Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        androidx.compose.material3.Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color.Green else Color.White
                        )
                    },
                    modifier = Modifier.padding(bottom = if (selectedTabIndex == index) 2.dp else 0.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị nội dung theo tab được chọn
        when (selectedTabIndex) {
            0 -> InUseTables(useTables)
            1 -> EmptyTables(emptyTables)
            2 -> BookingTable(bookingTables)
        }
    }
}

@Composable
fun InUseTables(tables: List<Table>) {
    var selectedTable by remember { mutableStateOf<Table?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(tables) { table ->
            TableItemRow(
                table = table,
                onClickDetail = {
                    selectedTable = table
                    showDialog = true // Mở dialog
                },
                onClickPay = {
                    // Thực hiện hành động thanh toán ở đây
                }
            )
        }
    }

    // Hiển thị dialog nếu có bàn được chọn và showDialog == true
    if (showDialog && selectedTable != null) {
        TableDetailDialog(
            table = selectedTable!!, // Bạn có thể giữ lại !! ở đây vì đã kiểm tra null trước đó
            orderItems = orderItems, // Truyền danh sách món đã oder
            onAddItem = { /* Hàm thêm món ở đây */ },
            onDismiss = {
                showDialog = false // Đặt showDialog về false để đóng dialog
            }
        )
    }
}


@Composable
fun EmptyTables(tables: List<Table>) {
    var selectedTable by remember { mutableStateOf<Table?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tables) { table ->
            BookingTableItemRow(
                table = table,
                onClick = {
                    selectedTable = table
                    showDialog = true
                }
            )
        }
    }

    if (showDialog && selectedTable != null) {
        BookingTableDialog(
            table = selectedTable!!,
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun BookingTable(tables: List<Table>) {
    var selectedTable by remember { mutableStateOf<Table?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tables) { table ->
            BookingTableItemRow(
                table = table,
                onClick = {
                    selectedTable = table
                    showDialog = true
                }
            )
        }
    }

    if (showDialog && selectedTable != null) {
        BookingTableDialog(
            table = selectedTable!!,
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun TableItemRow(table: Table, onClickDetail: () -> Unit, onClickPay: () -> Unit) {
    // Card bao quanh
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Tên bàn
            Text(
                text = "Bàn: ${table.tableName}",
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
                text = "Tổng tiền: ${table.totalAmount} đ",
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
                TextButton(
                    onClick = onClickDetail,
                    modifier = Modifier
                        .padding(end = 8.dp)
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
                    colors = ButtonDefaults.buttonColors(Color(0xFFE0FFE0))
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
    orderItems: List<com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.OrderItem>, // List of orders
    onAddItem: () -> Unit, // Function to add a new item
    onDismiss: () -> Unit
) {
    val maxItemsToShow = 5
    val displayedOrderItems =
        if (orderItems.size > maxItemsToShow) orderItems.take(maxItemsToShow) else orderItems
    val totalPrice = orderItems.sumOf { it.price * it.quantity }

    // Biến trạng thái để quản lý việc hiển thị AddItemsDialog
    var showAddItemsDialog by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Chi tiết",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color(0xFFFF6D00), // Orange color for title
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 8.dp),
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f), // Điều chỉnh để đảm bảo cột không chiếm hết màn hình
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

                // Header của bảng
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

                // Hiển thị tối đa 5 món và cuộn nếu nhiều hơn
                LazyColumn(
                    modifier = Modifier
                        .weight(1f) // Dùng weight để LazyColumn chiếm không gian và cuộn được
                        .padding(vertical = 8.dp)
                ) {
                    items(orderItems) { orderItem ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp), // Tăng chiều cao hàng
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

                // Hiển thị tổng tiền
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
                        text = "${totalPrice} đ",
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
                    modifier = Modifier.weight(1f),
                    enabled = true
                ) {
                    Text(text = "Thêm món", color = Color(0xFFFF6D00))
                }
            }

            // Hiển thị dialog để thêm món nếu showAddItemsDialog = true
            if (showAddItemsDialog) {
                AddItemsDialog(
                    products = com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.ListData, // Truyền danh sách sản phẩm
                    onDismiss = { showAddItemsDialog = false },
                    onAddItems = { selectedItems ->
                        // Xử lý khi món được thêm vào
                        showAddItemsDialog = false
                    }
                )
            }
        }
    )
}
@Composable
fun BookingTableItemRow(table: Table, onClick: () -> Unit) {
    // Card bao quanh
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
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
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333) // Màu chữ tối hơn
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Vị trí bàn
            Text(
                text = "Vị trí: ${table.location}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF666666) // Màu xám nhạt
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Sức chứa bàn
            Text(
                text = "Sức chứa: ${table.capacity}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF666666) // Màu xám nhạt
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Trạng thái bàn
            Text(
                text = "Trạng thái: ${table.status}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50) // Màu xanh lá cây đẹp mắt hơn
                )
            )
        }
    }
}

@Composable
fun BookingTableDialog(table: Table, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Chi tiết bàn ${table.tableName}",
                style = TextStyle(
                    color = Color(0xFF333333), // Màu chữ đậm hơn
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Column {
                Text(
                    text = "Vị trí: ${table.location}",
                    style = TextStyle(color = Color(0xFF666666)) // Màu chữ nhạt hơn
                )
                Text(
                    text = "Sức chứa: ${table.capacity}",
                    style = TextStyle(color = Color(0xFF666666)) // Màu chữ nhạt hơn
                )
                Text(
                    text = "Tên người đặt: ${table.bookerName}",
                    style = TextStyle(color = Color(0xFF666666)) // Màu chữ nhạt hơn
                )
                Text(
                    text = "Số điện thoại: ${table.bookerPhone}",
                    style = TextStyle(color = Color(0xFF666666)) // Màu chữ nhạt hơn
                )
                Text(
                    text = "Thời gian đặt: ${table.bookingTime}",
                    style = TextStyle(color = Color(0xFF666666)) // Màu chữ nhạt hơn
                )
                Text(
                    text = "Tiền cọc: ${table.depositAmount} đ",
                    style = TextStyle(
                        color = Color(0xFF333333),
                        fontWeight = FontWeight.SemiBold
                    ) // Màu chữ tối hơn và đậm
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF4CAF50), // Màu xanh lá cây
                    contentColor = Color.White // Màu chữ trắng cho nút
                )
            ) {
                Text(text = "Đóng")
            }
        }
    )
}

@Composable
fun AddItemsDialog(
    products: List<dataProduct>, // Danh sách sản phẩm
    onDismiss: () -> Unit, // Callback khi đóng dialog
    onAddItems: (Map<dataProduct, Int>) -> Unit // Callback khi thêm món
) {
    // Tạo biến trạng thái để lưu trữ số lượng của từng sản phẩm
    var quantities by remember { mutableStateOf(products.associateWith { 0 }.toMutableMap()) }

    // Tính tổng số lượng
    val totalQuantity = quantities.values.sum()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Thêm món",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color(0xFFFF6D00), // Màu cam cho tiêu đề
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f), // Giới hạn chiều cao của dialog
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bàn 4  Tầng 1", // Giả định bạn đang làm cho bàn 4 tầng 1
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Danh sách các sản phẩm với các nút tăng/giảm số lượng
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                ) {
                    items(products) { product ->
                        // Gọi ItemOrderProduct với số lượng và callback khi tăng/giảm số lượng
                        ItemOrderProduct(
                            item = product,
                            quantity = quantities[product] ?: 0,
                            onIncreaseClick = {
                                // Tăng số lượng sản phẩm
                                quantities[product] = (quantities[product] ?: 0) + 1
                            },
                            onDecreaseClick = {
                                // Giảm số lượng sản phẩm nếu số lượng > 0
                                if ((quantities[product] ?: 0) > 0) {
                                    quantities[product] = (quantities[product] ?: 0) - 1
                                }
                            }
                        )
                    }
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
                    onClick = { onAddItems(quantities) }, // Truyền số lượng món đã chọn
                    enabled = totalQuantity > 0, // Chỉ kích hoạt nếu tổng số lượng > 0
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCDD2)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Thêm món", color = Color(0xFFFF6D00))
                }
            }
        }
    )
}

