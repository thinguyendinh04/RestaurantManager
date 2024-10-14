package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.model.user.User
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.viewmodel.HomeEmployeeViewModel
import com.dinhthi2004.restaurantmanager.uilts.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setting(navigationController: NavHostController) {
    val homeEmployeeViewModel: HomeEmployeeViewModel = viewModel()
    val token = TokenManager.token
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var userId=TokenManager.userId
    val employees = homeEmployeeViewModel.employee.observeAsState().value
    employees?.let {
        fullName = it.full_name
        email = it.email
        phone = it.sdt
        role = it.role.toString()
    }
    LaunchedEffect(userId) {
        userId?.let {
            homeEmployeeViewModel.getUserInfo( "Bearer $token",it)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    Icons.Default.ArrowBackIosNew,
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { navigationController.navigate(Route.HomeSetting.screen) },
                    tint = Color.Black
                )
            }
            Text(
                text = "Sửa hồ sơ",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(modifier = Modifier.background(Color.White)) {
            Card(
                modifier = Modifier.padding(top = 20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
                border = BorderStroke(1.dp, color = Color(0xff99fdff))
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 80.dp)
                        .padding(10.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Nhập Họ và Tên
                    TextFieldWithLabel("Họ và Tên", fullName) { fullName = it }
                    // Nhập Email
                    TextFieldWithLabel("Email", email) { email = it }
                    // Nhập Số điện thoại
                    TextFieldWithLabel("Số điện thoại", phone) { phone = it }
                    TextFieldWithLabel("Role", role) { role = it }
                }
            }
            Box(modifier = Modifier.align(Alignment.TopCenter)) {
                Image(
                    painter = painterResource(id = R.drawable.man),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .clip(CircleShape)
                )
                Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                    IconButton(
                        onClick = {  },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White),
                        modifier = Modifier
                            .padding(8.dp)
                            .size(27.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PhotoCamera,
                            contentDescription = "",
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        }

        Divider(thickness = 1.dp, color = Color(0xffBCC1CA))
        Button(
            onClick = {
                // Tạo đối tượng User mới
                val updatedUser = userId?.let {
                    User(
                        created_at = "",
                        email = email,
                        full_name = fullName,
                        id = it,
                        image_url = "",
                        role = role.toInt(),
                        sdt = phone,
                        updated_at = "",
                        token = token
                    )
                }

                // Gọi hàm cập nhật thông tin người dùng trong ViewModel
                userId?.let { updatedUser?.let { it1 ->
                    homeEmployeeViewModel.updateUser("Bearer $token", it,
                        it1
                    )
                } }
                Log.d("jjjj", "Setting:${homeEmployeeViewModel} ")
                navigationController.navigate(Route.Welcome.screen) {
                    // Xóa các màn hình trước đó để không trở lại màn hình Setting
                    popUpTo(Route.Setting.screen) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(0.5f)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Lưu",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun TextFieldWithLabel(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .padding(horizontal = 20.dp)
    ) {
        Text(text = label, fontSize = 17.sp, color = Color.Black)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = "", color = Color.Gray) }
        )
    }
}

