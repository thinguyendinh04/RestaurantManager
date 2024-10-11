package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dinhthi2004.restaurantmanager.model.Table
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.viewmodel.TableViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogTable(onDismiss: () -> Unit) {
    val tableViewModel: TableViewModel = viewModel()
    val token = TokenManager.token

    var tableName by remember { mutableStateOf("") }
    var tableStatus by remember { mutableStateOf("") }
    var orderName by remember { mutableStateOf("") }

    // Xác thực dữ liệu nhập vào
    val isTableNameValid = tableName.isNotBlank()
    val isTableStatusValid = tableStatus.toIntOrNull() != null
    val isOrderNameValid = orderName.isNotBlank()

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Thêm Bàn Mới") },
        text = {
            Column {
                TextField(
                    value = tableName,
                    onValueChange = { tableName = it },
                    label = { Text("Tên bàn") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = tableStatus,
                    onValueChange = { tableStatus = it },
                    label = { Text("Trạng thái bàn") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = orderName,
                    onValueChange = { orderName = it },
                    label = { Text("Tên người đặt") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (isTableNameValid && isTableStatusValid && isOrderNameValid) {
                        val newTable = Table(
                            id = "", // ID có thể được tạo tự động từ backend
                            table_name = tableName,
                            table_status = tableStatus.toInt(),
                            oder_name = orderName,
                            id_account = null // Giả sử thông tin tài khoản không cần nhập ở đây
                        )
                        if (token != null) {
                            tableViewModel.addTable(token, newTable) {
                                tableViewModel.getTables(token)
                            }
                        }
                        onDismiss()
                    } else {

                    }
                },
                modifier = Modifier
                    .padding(top = 5.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff99fdff),
                    contentColor = Color.White
                )
            ) {
                Text("Xác nhận", color = Color.Black)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                modifier = Modifier
                    .padding(top = 5.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff565e6c),
                    contentColor = Color.White
                )
            ) {
                Text("Hủy")
            }
        }
    )
}
