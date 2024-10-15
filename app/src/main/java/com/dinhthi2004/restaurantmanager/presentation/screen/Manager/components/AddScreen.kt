package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.viewmodel.IngredientViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientDialog(
    onDismiss: () -> Unit,
    ingreViewModel: IngredientViewModel
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var createdAt by remember { mutableStateOf("") }
    var updatedAt by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // State to hold image URI
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    // Dialog UI
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Add Ingredient") },
        text = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Input for name
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Input for amount
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Error message display
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Button to pick an image
                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pick an Image")
                }

                // Display selected image
                imageUri?.let { uri ->
                    Card(
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // Kiểm tra nếu người dùng nhập đủ thông tin
                        if (name.isEmpty() || amount.isEmpty()) {
                            errorMessage = "Please fill in all fields"
                            return@Button
                        }

                        // Chuyển đổi Uri thành byte[] (nếu có ảnh)
                        val imageBytes = imageUri?.let { uri ->
                            try {
                                val inputStream = context.contentResolver.openInputStream(uri)
                                inputStream?.readBytes()?.also {
                                    inputStream.close()
                                }
                            } catch (e: Exception) {
                                Log.e("AddIngredientDialog", "Error reading image: ${e.localizedMessage}")
                                null
                            }
                        }

                        // Gửi request tạo nguyên liệu mới cùng hình ảnh
                        ingreViewModel.createIngredient(
                            name = name,
                            amount = amount.toInt(),
                            imageBytes = imageBytes,
                            createdAt = createdAt,
                            updatedAt = updatedAt
                        )

                        // Đóng dialog
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Add Ingredient")
                }
            }
        },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        },
        dismissButton = null // Có thể bỏ qua hoặc thêm nút hủy
    )
}
