package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientDialog(onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var ton by remember { mutableStateOf("") }
    var nhap by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> selectedImageUri = uri }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Thêm Nguyên Liệu Mới") },
        text = {
            Column {
                Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                    Text("Chọn ảnh")
                }

                selectedImageUri?.let { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = "Selected Image",
                        modifier = Modifier.width(200.dp).height(200.dp).align(Alignment.CenterHorizontally) .clip(shape = RoundedCornerShape(12.dp)),
                    )
                }

                TextField(value = name, onValueChange = { name = it }, label = { Text("Tên nguyên liệu") })
                TextField(value = ton, onValueChange = { ton = it }, label = { Text("Tồn kho") })
                TextField(value = nhap, onValueChange = { nhap = it }, label = { Text("Nhập hàng") })
            }
        },
        confirmButton = {
            Button(onClick = { onDismiss() },  modifier = Modifier
                .padding(top = 5.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff99fdff),
                    contentColor = Color.White
                )) {
                Text("Xác nhận", color = Color.Black)
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() },
                modifier = Modifier
                    .padding(top = 5.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff565e6c), // Màu nền của nút
                    contentColor = Color.White // Màu chữ
                )
            ) {
                Text("Hủy")

            }
        }
    )
}
