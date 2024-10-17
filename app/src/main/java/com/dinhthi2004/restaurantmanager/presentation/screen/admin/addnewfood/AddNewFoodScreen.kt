package com.dinhthi2004.restaurantmanager.presentation.screen.admin.addnewfood

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.dish.Dish
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.MenuManageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewFoodScreen(
    navController: NavController,
    menuViewModel: MenuManageViewModel = viewModel()
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    val typeId by remember { mutableStateOf(2) }
    var info by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    // Theo dõi trạng thái thông báo thành công và lỗi từ ViewModel
    val successMessage by menuViewModel.successMessage.collectAsState()
    val errorMessageState by menuViewModel.errorMessage.collectAsState()

    // Khi có thông báo thành công, điều hướng về màn hình trước
    LaunchedEffect(successMessage) {
        successMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            // Điều hướng về màn hình trước
            navController.navigateUp()
        }
    }

    // Khi có lỗi từ ViewModel, hiển thị thông báo
    LaunchedEffect(errorMessageState) {
        errorMessageState?.let {
            errorMessage = it
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Input cho tên món ăn
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Input cho giá
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price") },
                modifier = Modifier.fillMaxWidth()
            )

            // Hiển thị thông báo lỗi nếu có
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = androidx.compose.ui.graphics.Color.Red,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }

            // Input cho trạng thái món ăn
            OutlinedTextField(
                value = status,
                onValueChange = { status = it },
                label = { Text("Status") },
                modifier = Modifier.fillMaxWidth(),
            )

            // Input cho thông tin thêm về món ăn
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
                    if (name.isEmpty() || price.isEmpty() || status.isEmpty()) {
                        errorMessage = "Please fill in all fields"
                        return@Button
                    }

                    // Kiểm tra giá trị giá hợp lệ
                    val priceValue = price.toFloatOrNull()
                    if (priceValue == null) {
                        errorMessage = "Invalid price format"
                        return@Button
                    }

                    // Gửi request tạo món ăn mới, tạo đối tượng Dish
                    menuViewModel.createDish(
                        dish = Dish(
                            id = null,
                            name = name,
                            id_type = typeId,
                            information = info,
                            status = status,
                            price = priceValue.toString(),
                            image_url = null,
                            created_at = "",
                            updated_at = ""
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Add Food")
            }
        }
    }
}
