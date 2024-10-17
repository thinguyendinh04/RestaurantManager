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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.MenuManageViewModel
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.dinhthi2004.restaurantmanager.model.dish.Dish
import com.dinhthi2004.restaurantmanager.model.dish_type.Dish_type

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewFoodScreen(
    navController: NavController,
    viewModel: AddNewFoodViewModel = viewModel(),
    menuViewModel: MenuManageViewModel = viewModel()
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var typeId by remember { mutableStateOf("") }
    var info by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    // State to hold image URI
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val dishType by viewModel.Dish_Types.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedMealType by remember { mutableStateOf<Dish_type?>(null) }

    val addMealState by viewModel.addMealState.collectAsState()

    if (addMealState?.isSuccess == true) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, "Meal added successfully", Toast.LENGTH_SHORT).show()
            navController.navigateUp()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAllDishType()
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

            // Error message display
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = androidx.compose.ui.graphics.Color.Red,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }

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
                                typeId = dishtype.id.toString()
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
                    // Kiểm tra nếu người dùng nhập đủ thông tin
                    if (name.isEmpty() || price.isEmpty() || typeId.isEmpty() || status.isEmpty()) {
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
                            Log.e("AddNewFoodScreen", "Error reading image: ${e.localizedMessage}")
                            null
                        }
                    }

                    // Gửi request tạo món ăn mới cùng hình ảnh
                    menuViewModel.createDish(
                        name = name,
                        price = price.toFloatOrNull() ?: 0f,
                        status = status,
                        idType = typeId.toInt(),
                        information = info,
                        imageBytes = imageBytes // Gửi byte[] của ảnh vào ViewModel
                    )

                    navController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Add Food")
            }
        }
    }
}