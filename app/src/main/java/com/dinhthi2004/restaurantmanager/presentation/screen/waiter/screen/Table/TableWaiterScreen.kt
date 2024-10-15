import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.Table
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Invoice
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.dataTables
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.Table.InUseTables

@Composable
fun TableWaiterScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Bàn đang sử dụng", "Bàn trống", "Bàn đặt")

    // Sử dụng remember để lưu danh sách bàn sử dụng và bàn trống
    var useTables by remember { mutableStateOf(dataTables.filter { it.status == "Occupied" }) }
    var emptyTables by remember { mutableStateOf(dataTables.filter { it.status == "Empty" }) }
    var bookingTables = remember { dataTables.filter { it.status == "Booked" } }

    // Khởi tạo danh sách hóa đơn
    val invoices = remember { mutableStateListOf<Invoice>() }
    fun reloadTables() {
        // Cập nhật lại danh sách bàn trống và bàn đặt ngay sau khi có sự thay đổi
        emptyTables = dataTables.filter { it.status == "Empty" }
        bookingTables = dataTables.filter { it.status == "Booked" }
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
                    useTables = updatedTables // Cập nhật danh sách bàn đang sử dụng
                },
                onEmptyTableUpdate = { updatedEmptyTables ->
                    emptyTables = updatedEmptyTables // Cập nhật danh sách bàn trống sau khi thanh toán
                },
                invoices = invoices // Truyền danh sách hóa đơn
            )
            1 -> EmptyTables(
                tables = emptyTables,
                navigateToBooking = {
                    selectedTabIndex = 2 // Điều hướng sang tab "Bàn đặt"
                },
                reloadTables = { reloadTables() } // Truyền hàm reloadTables
            )
            2 -> BookingTable(tables = bookingTables)
        }
    }
}

@Composable
fun EmptyTables(
    tables: List<Table>,
    navigateToBooking: () -> Unit,
    reloadTables: () -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tables) { table ->
            EmpetyTableItemRow(
                table = table,
                onOrderClick = { /* Xử lý gọi món */ },
                onBookClick = {
                    // Hiển thị dialog đặt bàn và cập nhật bàn
                    showBookingDialog(table, reloadTables, navigateToBooking)
                },
                navigateToBooking = navigateToBooking,
                reloadTables = reloadTables // Truyền hàm reloadTables vào
            )
        }
    }
}
@Composable
fun showBookingDialog(
    table: Table,
    reloadTables: () -> Unit,
    navigateToBooking: () -> Unit
) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        BookingDialog(
            tableName = table.tableName,
            location = table.location,
            onDismiss = { showDialog = false },
            onConfirm = { bookerName, bookingTime, phone, deposit ->
                // Cập nhật thông tin bàn
                table.status = "Booked"
                table.bookerName = bookerName
                table.bookingTime = bookingTime
                table.bookerPhone = phone
                table.depositAmount = deposit.toDouble()

                // Làm mới danh sách bàn trống và điều hướng sang màn hình bàn đặt
                reloadTables()
                navigateToBooking()

                showDialog = false // Đóng dialog
            }
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
                onOrderClick = {
                    selectedTable = table
                    showDialog = true
                } ,
                onBookClick={
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
fun EmpetyTableItemRow(
    table: Table,
    onOrderClick: () -> Unit,
    onBookClick: @Composable () -> Unit,
    navigateToBooking: () -> Unit,
    reloadTables: () -> Unit // Bổ sung hàm reloadTables
) {
    var showBookingDialog by remember { mutableStateOf(false) }

    // Card bao quanh
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
            horizontalArrangement = Arrangement.SpaceBetween // Đặt hai nút ở bên phải
        ) {
            Column(
                modifier = Modifier.weight(1f)
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
                    text = "Số lượng: ${table.capacity}",
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
                        color = if (table.status == "Empty") Color(0xFF4CAF50) else Color(0xFFFF5722) // Xanh lá cây hoặc cam tùy theo trạng thái
                    )
                )
            }

            // Nút "Đặt bàn" và "Gọi món"
            Column(
                horizontalAlignment = Alignment.End, // Canh nút sang phải
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Button(
                    onClick = { showBookingDialog = true }, // Hiển thị dialog đặt bàn
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5722), // Màu cam cho nút "Đặt bàn"
                        contentColor = Color.White // Màu chữ trắng
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(text = "Đặt bàn")
                }

                Button(
                    onClick = onOrderClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50), // Màu xanh lá cho nút "Gọi món"
                        contentColor = Color.White // Màu chữ trắng
                    )
                ) {
                    Text(text = "Gọi món")
                }
            }
        }
    }

    // Hiển thị dialog đặt bàn
    if (showBookingDialog) {
        BookingDialog(
            tableName = table.tableName,
            location = table.location,
            onDismiss = { showBookingDialog = false },
            onConfirm = { bookerName, bookingTime, phone, deposit ->
                // Cập nhật trạng thái bàn và thông tin người đặt
                table.status = "Booking"
                table.bookerName = bookerName
                table.bookingTime = bookingTime
                table.bookerPhone = phone
                table.depositAmount = deposit.toDouble()

                showBookingDialog = false // Đóng dialog sau khi xác nhận

                reloadTables() // Làm mới danh sách bàn
                navigateToBooking() // Điều hướng sang màn hình "Bàn đặt"
            }
        )
    }
}
@Composable
fun BookingDialog(
    tableName: String,
    location: String,
    onDismiss: () -> Unit,
    onConfirm: (bookerName: String, bookingTime: String, phone: String, deposit: String) -> Unit
) {
    var bookerName by remember { mutableStateOf("") }
    var bookingTime by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var deposit by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") } // Thông báo lỗi

    fun validate(): Boolean {
        errorMessage = ""
        if (bookerName.isBlank()) {
            errorMessage = "Họ tên không được để trống!"
            return false
        }
        if (!phone.matches(Regex("^\\d{10}$"))) {
            errorMessage = "Số điện thoại phải có 10 chữ số!"
            return false
        }
        if (deposit.toDoubleOrNull() == null || deposit.toDouble() < 0) {
            errorMessage = "Số tiền cọc phải là số và >= 0!"
            return false
        }
        return true
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Đặt bàn",
                style = TextStyle(
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )
        },
        text = {
            Column {
                Text(text = "Bàn $tableName $location")

                // Nhập tên người đặt
                OutlinedTextField(
                    value = bookerName,
                    onValueChange = { bookerName = it },
                    label = { Text(text = "Nhập họ tên người đặt") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                // Nhập thời gian đặt bàn
                OutlinedTextField(
                    value = bookingTime,
                    onValueChange = { bookingTime = it },
                    label = { Text(text = "Thời gian đặt bàn") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                // Nhập số điện thoại
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text(text = "Số điện thoại") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                // Nhập số tiền cọc
                OutlinedTextField(
                    value = deposit,
                    onValueChange = { deposit = it },
                    label = { Text(text = "Số tiền cọc") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                // Hiển thị thông báo lỗi nếu có
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (validate()) {
                        onConfirm(bookerName, bookingTime, phone, deposit) // Gửi dữ liệu nhập vào
                        onDismiss() // Đóng dialog sau khi xác nhận
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5722), // Màu cam
                    contentColor = Color.White // Màu chữ trắng
                )
            ) {
                Text(text = "Xác nhận")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE0E0E0), // Màu xám nhạt
                    contentColor = Color.Black // Màu chữ đen
                )
            ) {
                Text(text = "Đóng")
            }
        }
    )
}

@Composable
fun BookingTableItemRow(table: Table, onOrderClick: () -> Unit, onBookClick: () -> Unit) {
    // Card bao quanh
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Do nothing on card click, as buttons handle actions */ },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween // Đặt hai nút ở bên phải
        ) {
            Column(
                modifier = Modifier.weight(1f)
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
                    text = "Số lượng: ${table.capacity}",
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
                        color = if (table.status == "Bàn trống") Color(0xFF4CAF50) else Color(0xFFFF5722) // Xanh lá cây hoặc cam tùy theo trạng thái
                    )
                )
            }

            // Nút "Đặt bàn" và "Gọi món"
            Column(
                horizontalAlignment = Alignment.End, // Canh nút sang phải
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Button(
                    onClick = onBookClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5722), // Màu cam cho nút "Đặt bàn"
                        contentColor = Color.White // Màu chữ trắng
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(text = "Đặt bàn")
                }

                Button(
                    onClick = onOrderClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50), // Màu xanh lá cho nút "Gọi món"
                        contentColor = Color.White // Màu chữ trắng
                    )
                ) {
                    Text(text = "Gọi món")
                }
            }
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


