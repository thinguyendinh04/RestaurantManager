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
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.viewmodel.IngredientViewModel
import com.dinhthi2004.restaurantmanager.viewmodel.TableViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientDialog(onDismiss: () -> Unit) {
    val ingreViewModel: IngredientViewModel = viewModel()
    val token = TokenManager.token

    var ingredient_name by remember { mutableStateOf("") }
    var ingredients_amount by remember { mutableStateOf("") }
    val isTableNameValid = ingredient_name.isNotBlank()
    val isOrderNameValid = ingredients_amount.isNotBlank()
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Thêm Nguyên Liệu Mới") },
        text = {
            Column {

                TextField(value = ingredient_name,
                    onValueChange = { ingredient_name = it },
                    label = { Text("Tên nguyên liệu") })
                TextField(value = ingredients_amount, onValueChange = { ingredients_amount = it }, label = { Text("Số Lượng") })
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (isTableNameValid  && isOrderNameValid) {
                        val newIngredient = Ingredient(
                            ingredient_name = ingredient_name,
                            ingredients_amount = ingredients_amount.toInt(),

                        )
                        if (token != null) {
                            ingreViewModel.addingredient(token, newIngredient) {
                                ingreViewModel.getIngredients(token)
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
