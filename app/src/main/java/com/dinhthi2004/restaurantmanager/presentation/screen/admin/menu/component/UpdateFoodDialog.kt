package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.dish.Dish
import com.dinhthi2004.restaurantmanager.model.dish_type.Dish_type
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.addnewfood.AddNewFoodViewModel
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.MenuManageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateFoodDialog(
    dish: Dish,
    onDismiss: () -> Unit,
    onUpdateFood: () -> Unit,
    viewModel: MenuManageViewModel = viewModel(),
    addViewModel: AddNewFoodViewModel = viewModel(),
) {
    var name by remember { mutableStateOf(dish.name) }
    var price by remember { mutableStateOf(dish.price) }
    var status by remember { mutableStateOf(dish.status) }
    var typeId by remember { mutableStateOf(dish.id_type) }
    var info by remember { mutableStateOf(dish.information) }
    val dishType by addViewModel.Dish_Types.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedMealType by remember { mutableStateOf<Dish_type?>(null) }

    LaunchedEffect(Unit) {
        addViewModel.getAllDishType()
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_dish),
            contentDescription = ""
        )

        // Input for name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Input for price
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )

        // Input for status
        OutlinedTextField(
            value = status,
            onValueChange = { status = it },
            label = { Text("Status") },
            modifier = Modifier.fillMaxWidth(),
        )

        // Dropdown for meal type
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                value = selectedMealType?.name ?: "Select Meal Type",
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                label = { Text("Meal Type") }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dishType.forEach { dishtype ->
                    DropdownMenuItem(
                        text = { Text(dishtype.name) },
                        onClick = {
                            selectedMealType = dishtype
                            typeId = dishtype.id
                            expanded = false
                        }
                    )
                }
            }
        }

        // Input for info
        OutlinedTextField(
            value = info,
            onValueChange = { info = it },
            label = { Text("Info") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val priceFloat = price.toFloatOrNull() ?: run {
                    Log.e("UpdateFoodDialog", "Giá nhập vào không hợp lệ.")
                    return@Button
                }

                if (name.isNotEmpty() && priceFloat > 0 && status.isNotEmpty()) {
                    dish.id?.let {
                        viewModel.updateDish(
                            id = it,
                            name = name,
                            price = priceFloat,
                            status = status,
                            idType = typeId.toInt(),
                            information = info,
                            imageUri = null
                        )
                        Log.d("UpdateFoodDialog", "Món ăn đã được cập nhật.")
                        onUpdateFood()
                    }
                } else {
                    Log.e("UpdateFoodDialog", "Dữ liệu nhập vào không hợp lệ.")
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Update Food")
        }
    }
}
