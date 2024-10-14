package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.Ingredient
import com.dinhthi2004.restaurantmanager.model.Table
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.model.ingredient.IngredientData
import com.dinhthi2004.restaurantmanager.viewmodel.IngredientViewModel
import com.dinhthi2004.restaurantmanager.viewmodel.TableViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientDialog(onDismiss: () -> Unit) {
    val ingreViewModel: IngredientViewModel = viewModel()
    val token = TokenManager.token;

    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
val created_at by remember { mutableStateOf("") }
    val updated_at by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Thêm Nguyên Liệu Mới") },
        text = {
            Column {

                TextField(value = name,
                    onValueChange = { name = it },
                    label = { Text("Tên nguyên liệu") })
                TextField(value = amount, onValueChange = { amount = it }, label = { Text("Số Lượng") })
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newIngre = IngredientData(
                        id = null, // ID có thể được tạo tự động từ backend
                        name = name,
                        amount = amount.toInt(),
                        created_at = created_at,
                        updated_at = updated_at
                    )
                    if (token != null) {
                        ingreViewModel.addingredient(token, newIngre) {
                            ingreViewModel.getIngredients(token)
                        }
                    }
                    onDismiss()
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
