package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.data.Employee
import com.dinhthi2004.restaurantmanager.model.Account
import com.dinhthi2004.restaurantmanager.model.user.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeCard(employee: User, onClick: () -> Unit) {
    val painter = rememberImagePainter(
        data = employee.image_url, // employee.image_url là URL của hình ảnh
        builder = {
            crossfade(true) // Tùy chọn hiệu ứng chuyển tiếp
        }
    )
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            modifier = Modifier.clip(RoundedCornerShape(16.dp)),
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp, start = 30.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            ) {
                Column {

                    // Nội dung dialog
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.man),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.LightGray),
                                contentScale = ContentScale.FillHeight
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(text = "Họ tên: ${employee.full_name}")
                                Text(text = "Email: ${employee.email}")
                                Text(text = "SDT: ${employee.sdt}")
                                Text(
                                    text = "${getRoleText(employee.role)}",
                                    color = Color.Red
                                )

                            }
                        }

                    }
                    Button(
                        onClick = { showDialog = false },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff565e6c))
                    ) {
                        Text("Đóng", color = Color.Black)
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(10.dp)
            .clickable { onClick() }
            .border(BorderStroke(1.dp, Color(0xff565E6C)), RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.man),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = employee.full_name, fontSize = 14.sp, color = Color.Black)

                Text(text = "${getRoleText(employee.role)}", fontSize = 14.sp, color = Color.Black)

            }
            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier.align(Alignment.CenterVertically).padding(bottom = 18.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Description,
                    contentDescription = "Xem chi tiết",
                    tint = Color(0xff565e6c)
                )
            }
        }
    }
}

fun getRoleText(role: Int): String {
    return when (role) {
        1 -> "Quản lí"
        2 -> "Nhan vien"
        else -> "Unknown"
    }
}
