import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.api.ApiService
import com.dinhthi2004.restaurantmanager.api.HttpReq
import com.dinhthi2004.restaurantmanager.model.Table
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.bookedTables
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.emptyTables
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.occupiedTables

@Composable
fun TableWaiterScreen(navController: NavHostController) {
    val api = HttpReq.getInstance()

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Tất cả", "Bàn trống", "Bàn đặt")

    var allTables by remember { mutableStateOf<List<Table>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val token = TokenManager.token
            val response = api.getTables("Bearer $token")

            // Ghi lại phản hồi từ API để kiểm tra
            if (response.isSuccessful) {
                val tableResponse = response.body()
                Log.d("API Response", tableResponse.toString())
                allTables = tableResponse?.tables ?: emptyList()
                Log.d("API Response", "Data: ${response.body()}")
            } else {
                Log.e("TableWaiterScreen", "Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("TableWaiterScreen", "Exception: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        if (allTables.isEmpty()) {
            // Thông báo không có bàn nào
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Không có bàn nào để hiển thị.", modifier = Modifier.padding(16.dp))
            }
        } else {
            // Hiển thị danh sách các bàn khi tải xong
            AllTables(allTables)
        }
    }
}

@Composable
fun AllTables(tables: List<Table>) {
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
fun TableItemRow(table: Table, onClickDetail: () -> Unit, onClickPay: () -> Unit) {

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
                text = "Bàn ${table.table_name}",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Vị trí bàn
            Text(
                text = "Trạng thái: ${if (table.table_status == 0) "Còn trống" else "Đang sử dụng"}",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Nút Chi tiết
                TextButton(
                    onClick = onClickDetail,
                    modifier = Modifier.padding(end = 8.dp)
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
                    colors = if (table.table_status == 0) ButtonDefaults.buttonColors(Color(
                        0xFFC24E4D
                    )
                    ) else ButtonDefaults.buttonColors(Color(
                        0xFF55905A
                    )
                    )
                ) {
                    Text(text = if (table.table_status == 0) "Đặt bàn" else "Thanh toán", color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun TableDetailDialog(table: Table, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Chi tiết bàn")
        },
        text = {
            Column {
                Text(text = "Tên bàn: ${table.table_name}")
                Text(text = "Trạng thái: ${if (table.table_status == 0) "Còn trống" else "Đang sử dụng"}")
                Text(text = "Tên người đặt: ${table.oder_name}")

            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(text = "Đóng")
            }
        }
    )
}
