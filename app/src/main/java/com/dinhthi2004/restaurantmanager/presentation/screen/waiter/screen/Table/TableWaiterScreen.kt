import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.model.table.Tabledata
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Invoice
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.tableSampleList
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.Table.InUseTables

@Composable
fun TableWaiterScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Bàn đang sử dụng", "Bàn trống", "Bàn đặt")

    // Sử dụng remember để lưu danh sách bàn sử dụng và bàn trống
    var useTables by remember { mutableStateOf(tableSampleList.filter { it.status == "Occupied" }) }
    var emptyTables by remember { mutableStateOf(tableSampleList.filter { it.status == "Available" }) }
    var bookingTables by remember { mutableStateOf(tableSampleList.filter { it.status == "Booked" }) }

    // Khởi tạo danh sách hóa đơn
    val invoices = remember { mutableStateListOf<Invoice>() }

    fun reloadTables() {
        // Cập nhật lại danh sách bàn trống và bàn đặt ngay sau khi có sự thay đổi
        emptyTables = tableSampleList.filter { it.status == "Empty" }
        bookingTables = tableSampleList.filter { it.status == "Booked" }
    }

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

        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị nội dung tương ứng với tab đã chọn
        when (selectedTabIndex) {
            0 -> InUseTables(
                tables = useTables,
                onTableUpdate = { updatedTables ->
                    useTables = updatedTables
                },
                onEmptyTableUpdate = { updatedEmptyTables ->
                    emptyTables = updatedEmptyTables
                },
                invoices = invoices
            )
            1 -> EmptyTables(
                tables = emptyTables,
                navigateToBooking = { selectedTabIndex = 2 },
                reloadTables = { reloadTables() }
            )
            2 -> BookingTable(tables = bookingTables)
        }
    }
}

@Composable
fun EmptyTables(
    tables: List<Tabledata>,
    navigateToBooking: () -> Unit,
    reloadTables: () -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tables) { table ->
            EmptyTableItemRow(
                table = table,
                onOrderClick = { /* Xử lý gọi món */ },
                onBookClick = {
                    showBookingDialog(table, reloadTables, navigateToBooking)
                },
                navigateToBooking = navigateToBooking,
                reloadTables = reloadTables
            )
        }
    }
}

@Composable
fun showBookingDialog(
    table: Tabledata,
    reloadTables: () -> Unit,
    navigateToBooking: () -> Unit
) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        BookingDialog(
            tableName = table.table_name,
            location = table.customer_name,
            onDismiss = { showDialog = false },
            onConfirm = { bookerName, bookingTime ->
                table.status = "Booked"
                reloadTables()
                navigateToBooking()
                showDialog = false
            }
        )
    }
}

@Composable
fun BookingTable(tables: List<Tabledata>) {
    var selectedTable by remember { mutableStateOf<Tabledata?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tables) { table ->
            BookingTableItemRow(
                table = table,
                onOrderClick = {
                    selectedTable = table
                    showDialog = true
                },
                onBookClick = {
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
fun EmptyTableItemRow(
    table: Tabledata,
    onOrderClick: () -> Unit,
    onBookClick: @Composable () -> Unit,
    navigateToBooking: () -> Unit,
    reloadTables: () -> Unit
) {
    var showBookingDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Bàn ${table.table_name}",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333)),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Trạng thái: ${table.status}",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color(0xFF666666)),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(start = 16.dp)) {
                Button(
                    onClick = { showBookingDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722), contentColor = Color.White),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(text = "Đặt bàn")
                }

                Button(
                    onClick = onOrderClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50), contentColor = Color.White)
                ) {
                    Text(text = "Gọi món")
                }
            }
        }
    }

    if (showBookingDialog) {
        BookingDialog(
            tableName = table.table_name,
            location = table.customer_name,
            onDismiss = { showBookingDialog = false },
            onConfirm = { bookerName, bookingTime ->
                table.status = "Booked"
                reloadTables()
                navigateToBooking()
                showBookingDialog = false
            }
        )
    }
}

@Composable
fun BookingDialog(
    tableName: String,
    location: String,
    onDismiss: () -> Unit,
    onConfirm: (bookerName: String, bookingTime: String) -> Unit
) {
    var bookerName by remember { mutableStateOf("") }
    var bookingTime by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    fun validate(): Boolean {
        errorMessage = ""
        if (bookerName.isBlank()) {
            errorMessage = "Họ tên không được để trống!"
            return false
        }
        return true
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Đặt bàn",
                style = TextStyle(color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold, fontSize = 24.sp)
            )
        },
        text = {
            Column {
                Text(text = "Bàn $tableName $location")
                OutlinedTextField(
                    value = bookerName,
                    onValueChange = { bookerName = it },
                    label = { Text(text = "Nhập họ tên người đặt") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    value = bookingTime,
                    onValueChange = { bookingTime = it },
                    label = { Text(text = "Thời gian đặt bàn") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (validate()) {
                        onConfirm(bookerName, bookingTime)
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722), contentColor = Color.White)
            ) {
                Text(text = "Xác nhận")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0), contentColor = Color.Black)
            ) {
                Text(text = "Đóng")
            }
        }
    )
}

@Composable
fun BookingTableItemRow(table: Tabledata, onOrderClick: () -> Unit, onBookClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Bàn ${table.table_name}",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333)),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Trạng thái: ${table.status}",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = if (table.status == "Bàn trống") Color(0xFF4CAF50) else Color(0xFFFF5722))
                )
            }

            Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(start = 16.dp)) {
                Button(
                    onClick = onBookClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722), contentColor = Color.White),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(text = "Đặt bàn")
                }

                Button(
                    onClick = onOrderClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50), contentColor = Color.White)
                ) {
                    Text(text = "Gọi món")
                }
            }
        }
    }
}

@Composable
fun BookingTableDialog(table: Tabledata, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Chi tiết bàn ${table.table_name}",
                style = TextStyle(color = Color(0xFF333333), fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Column {
                Text(text = "Tên người đặt: ${table.customer_name}", style = TextStyle(color = Color(0xFF666666)))
                Text(text = "Thời gian đặt: ${table.created_at}", style = TextStyle(color = Color(0xFF666666)))
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50), contentColor = Color.White)
            ) {
                Text(text = "Đóng")
            }
        }
    )
}
