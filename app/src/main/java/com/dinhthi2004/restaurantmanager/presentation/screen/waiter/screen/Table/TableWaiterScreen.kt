import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.model.table.Tabledata
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.dishSampleList
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.tableSampleList
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.Table.AddItemsDialog
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.Table.InUseTables
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.dinhthi2004.restaurantmanager.model.Order
import com.dinhthi2004.restaurantmanager.model.OrderData
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.WaiterHomeViewModel
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.WaiterOrderViewModel
import kotlin.random.Random

var waiterTableViewModel: WaiterTableViewModel? = null
var waiterHomeViewModel: WaiterHomeViewModel? = null
var waiterOrderViewModel: WaiterOrderViewModel? = null

@Composable
fun TableWaiterScreen(navController: NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Bàn đang sử dụng", "Bàn trống", "Bàn đặt")

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    waiterTableViewModel = viewModel()
    waiterHomeViewModel = viewModel()
    waiterOrderViewModel = viewModel()

    LaunchedEffect(lifecycleState) {
        when(lifecycleState){
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {
                waiterTableViewModel!!.getTables()
                waiterHomeViewModel!!.getMeals()
            }
            Lifecycle.State.RESUMED -> {
                waiterTableViewModel!!.getTables()
            }
        }
    }
    val tables by waiterTableViewModel!!.tables.observeAsState(emptyList())

    var useTables by remember { mutableStateOf(tables) }
    var emptyTables by remember { mutableStateOf(tables) }
    var bookingTables by remember { mutableStateOf(tables) }

    // Sử dụng remember để lưu danh sách bàn sử dụng và bàn trống
    if (tables.isNotEmpty()){
        useTables = tables.filter { it.status == "Occupied" }
        emptyTables = tables.filter {it.status == "Available"}
        bookingTables = tables.filter { it.status == "Booked" }
    }

    fun reloadTables() {
        waiterTableViewModel!!.getTables()
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
                }
            )
            1 -> EmptyTables(
                tables = emptyTables,
                navigateToBooking = {
//                    selectedTabIndex = 2
                },
                reloadTables = {
                    reloadTables()
                }
            )
            2 -> BookingTable(tables = bookingTables,
                reloadTables = {
                    reloadTables()
            })
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
            onDismiss = { showDialog = false },
            onConfirm = { bookerName->
                table.status = "Booked"
                reloadTables()
                navigateToBooking()
                showDialog = false
            }
        )
    }
}

@Composable
fun BookingTable(
    tables: List<Tabledata>,
    reloadTables: () -> Unit
) {
    var selectedTable by remember { mutableStateOf<Tabledata?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tables) { table ->
            BookingTableItemRow(
                table = table,
                onBookClick = {
                    selectedTable = table
                    showDialog = true
                },
                reloadTables = reloadTables // Truyền hàm reloadTables để cập nhật danh sách bàn sau khi thêm món
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
    navigateToBooking: () -> Unit,
    reloadTables: () -> Unit
) {
    var showBookingDialog by remember { mutableStateOf(false) }
    var showAddItemsDialog by remember { mutableStateOf(false) }

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
                    onClick = { showAddItemsDialog = true }, // Mở dialog thêm món
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50), contentColor = Color.White)
                ) {
                    Text(text = "Thêm món")
                }
            }
        }
    }

    if (showBookingDialog) {
        BookingDialog(
            tableName = table.table_name,
            onDismiss = { showBookingDialog = false },
            onConfirm = { bookerName ->
                table.customer_name = bookerName
                table.status = "Booked"
//                println(table)
                waiterTableViewModel?.updateTable(table.id!!.toString(), newTableData = table)
                reloadTables()
                navigateToBooking()
                showBookingDialog = false
            }
        )
    }

    if (showAddItemsDialog) {
        AddItemsDialog(
            products = waiterHomeViewModel?.meals?.value!!, // Chọn món từ danh sách sẵn có
            onDismiss = { showAddItemsDialog = false },
            onAddItems = { selectedItems ->
                for (meal in waiterHomeViewModel?.meals?.value!!){
                    val amount = selectedItems[meal]
                    if(amount != 0){
//                        println(Order(table.id!!, meal.id!!, amount!!))
                        waiterOrderViewModel!!.addOrder(Order(table.id!!, meal.id!!, amount!!))
                    }
                }
                table.customer_name = "Không có"
                table.status = "Occupied"
                waiterTableViewModel?.updateTable(table.id!!.toString(), newTableData = table)
                reloadTables() // Cập nhật lại danh sách bàn
                showAddItemsDialog = false
            }
        )
    }
}

@Composable
fun BookingDialog(
    tableName: String,
    onDismiss: () -> Unit,
    onConfirm: (bookerName: String) -> Unit
) {
    var bookerName by remember { mutableStateOf("") }

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
                Text(text = "Bàn $tableName")
                OutlinedTextField(
                    value = bookerName,
                    onValueChange = { bookerName = it },
                    label = { Text(text = "Nhập họ tên người đặt") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (validate()) {
                        onConfirm(bookerName)
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
fun BookingTableItemRow(
    table: Tabledata,
    onBookClick: () -> Unit,
    reloadTables: () -> Unit // Thêm tham số reloadTables){}
) {
    var showAddItemsDialog by remember { mutableStateOf(false) }

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
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = if (table.status == "Available") Color(0xFF4CAF50) else Color(0xFFFF5722))
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
                    onClick = { showAddItemsDialog = true }, // Mở dialog thêm món
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50), contentColor = Color.White)
                ) {
                    Text(text = "Thêm món")
                }
            }
        }
    }

    if (showAddItemsDialog) {
        AddItemsDialog(
            products = waiterHomeViewModel!!.meals.value!!, // Chọn món từ danh sách sẵn có
            onDismiss = { showAddItemsDialog = false },
            onAddItems = { selectedItems ->
                for (meal in waiterHomeViewModel?.meals?.value!!){
                    val amount = selectedItems[meal]
                    if(amount != 0){
//                        println(Order(table.id!!, meal.id!!, amount!!))
                        waiterOrderViewModel!!.addOrder(Order(table.id!!, meal.id!!, amount!!))
                    }
                }
                table.status = "Occupied"
                waiterTableViewModel?.updateTable(table.id!!.toString(), newTableData = table)
                reloadTables() // Cập nhật lại danh sách bàn
                showAddItemsDialog = false
            }
        )
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
