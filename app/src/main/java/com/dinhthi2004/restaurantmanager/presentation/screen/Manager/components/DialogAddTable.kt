package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dinhthi2004.restaurantmanager.model.table.Tabledata
import com.dinhthi2004.restaurantmanager.viewmodel.TableViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogTable(onDismiss: () -> Unit) {
    val tableViewModel: TableViewModel = viewModel()
    val token = "123"
    val context= LocalContext.current
    var tableName by remember { mutableStateOf("") }
    var tableStatus by remember { mutableStateOf("") }
    var orderName by remember { mutableStateOf("") }
    var created_at by remember { mutableStateOf("") }
    var updated_at by remember { mutableStateOf("") }

    // Xác thực dữ liệu nhập vào
    val isTableNameValid = tableName.isNotBlank()
    val isTableStatusValid = tableStatus.isNotBlank()
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
                        val newTable = Tabledata(
                            id = null,// ID có thể được tạo tự động từ backend
                            table_name = tableName,
                            status = tableStatus,
                            customer_name = orderName,
                            created_at=created_at,
                            updated_at=updated_at
                        )
                        if (token.isNotEmpty()) {
                            tableViewModel.addTable(token, newTable) {
                                tableViewModel.getTables(token)
                               Toast.makeText(context,"Thêm thành công",Toast.LENGTH_SHORT).show()
                            }
                        }
                        onDismiss()
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
