import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.material3.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Invoice
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Table.ItemOrderProduct
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.ListData
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.OrderItem
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.dataProduct
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.dataTables
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.Table.InUseTables
import java.text.DecimalFormat

@Composable
fun TableWaiterScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Bàn đang sử dụng", "Bàn trống", "Bàn đặt")

    // Sử dụng remember để lưu danh sách bàn sử dụng và bàn trống
    var useTables by remember { mutableStateOf(dataTables.filter { it.status == "Occupied" }) }
    var emptyTables by remember { mutableStateOf(dataTables.filter { it.status == "Empty" }) }
    val bookingTables = remember { dataTables.filter { it.status == "Booked" } }

    // Khởi tạo danh sách hóa đơn
    val invoices = remember { mutableStateListOf<Invoice>() }

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
            1 -> EmptyTables(tables = emptyTables)
            2 -> BookingTable(tables = bookingTables)
        }
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


