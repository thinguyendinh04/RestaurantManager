package com.dinhthi2004.restaurantmanager.presentation.screen.admin.addnewfood

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.MenuManageViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.unit.dp
import com.dinhthi2004.restaurantmanager.model.Meal

import com.dinhthi2004.restaurantmanager.model.MealType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewFoodScreen(
    navController: NavController,
    viewModel: AddNewFoodViewModel = viewModel(),
) {
    // Các state cho từng thuộc tính của Meal
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var typeId by remember { mutableStateOf("") }
    var info by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }

    // State để chọn loại món ăn (dropdown)
    val mealTypes by viewModel.mealTypes.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedMealType by remember { mutableStateOf<MealType?>(null) }

    val context = LocalContext.current
    val addMealState by viewModel.addMealState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllMealTypes()
    }

    // Khi thêm món ăn thành công, quay lại màn hình trước
    if (addMealState?.isSuccess == true) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, "Meal added successfully", Toast.LENGTH_SHORT).show()
            navController.navigateUp()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add New Food") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Các trường nhập liệu cho từng thuộc tính của Meal
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price") },
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = status,
                onValueChange = { status = it },
                label = { Text("Status") },
                modifier = Modifier.fillMaxWidth(),
            )

            // Dropdown chọn loại món ăn (Meal Type)
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
                    mealTypes.forEach { mealType ->
                        DropdownMenuItem(
                            text = { Text(mealType.name) },
                            onClick = {
                                selectedMealType = mealType
                                expanded = false
                                typeId = mealType._id
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = info,
                onValueChange = { info = it },
                label = { Text("Info") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = rating,
                onValueChange = { rating = it },
                label = { Text("Rating") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nút để thêm món ăn
            Button(
                onClick = {
                    val priceValue = price.toIntOrNull()
                    val statusValue = status.toIntOrNull()
                    val ratingValue = rating.toDoubleOrNull()

                    if (name.isNotBlank() && priceValue != null && statusValue != null && selectedMealType != null && ratingValue != null) {
                        viewModel.addNewMeal(
                            Meal(
                                _id = "",
                                name = name,
                                price = priceValue,
                                status = statusValue,
                                type_id = selectedMealType!!._id, // Sử dụng typeId đã chọn
                                info = info,
                                rating = ratingValue
                            )
                        )
                    } else {
                        Toast.makeText(
                            context,
                            "Please fill out all fields correctly",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Food")
            }
        }
    }
}


