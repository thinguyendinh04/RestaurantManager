import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
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
                    showDialog = true
                },
                onClickPay = {
                    // Thực hiện hành động thanh toán ở đây
                }
            )
        }
    }

    // Hiển thị dialog nếu có bàn được chọn
    if (showDialog && selectedTable != null) {
        TableDetailDialog(table = selectedTable!!, onDismiss = { showDialog = false })
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
fun TableDetailDialog(table: Table, onDismiss: () -> Unit) {
    // Dialog hiển thị chi tiết bàn
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Chi tiết bàn")
        },
        text = {
            Column {
                Text(text = "Tên bàn: ${table.tableName}")
                Text(text = "Vị trí: ${table.location}")
                Text(text = "Số khách: ${table.currentGuests}/${table.capacity}")
                Text(text = "Tổng tiền: ${table.totalAmount}")
                Text(text = "Trạng thái thanh toán: ${table.paymentStatus}")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(text = "Đóng")
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
            Text(text = "Chi tiết bàn ${table.tableName}",
                style = TextStyle(
                    color = Color(0xFF333333), // Màu chữ đậm hơn
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Column {
                Text(text = "Vị trí: ${table.location}",
                    style = TextStyle(color = Color(0xFF666666)) // Màu chữ nhạt hơn
                )
                Text(text = "Sức chứa: ${table.capacity}",
                    style = TextStyle(color = Color(0xFF666666)) // Màu chữ nhạt hơn
                )
                Text(text = "Tên người đặt: ${table.bookerName}",
                    style = TextStyle(color = Color(0xFF666666)) // Màu chữ nhạt hơn
                )
                Text(text = "Số điện thoại: ${table.bookerPhone}",
                    style = TextStyle(color = Color(0xFF666666)) // Màu chữ nhạt hơn
                )
                Text(text = "Thời gian đặt: ${table.bookingTime}",
                    style = TextStyle(color = Color(0xFF666666)) // Màu chữ nhạt hơn
                )
                Text(text = "Tiền cọc: ${table.depositAmount} đ",
                    style = TextStyle(color = Color(0xFF333333), fontWeight = FontWeight.SemiBold) // Màu chữ tối hơn và đậm
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
