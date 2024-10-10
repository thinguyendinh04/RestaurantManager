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
import com.dinhthi2004.restaurantmanager.data.Employee

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeCard(employee: Employee, onClick: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val total=employee.thuong + employee.luongCb
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
                                painter = painterResource(id = employee.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.LightGray),
                                contentScale = ContentScale.FillHeight
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(text = "Họ tên: ${employee.name}")
                                Text(text = "Chức vụ: ${employee.job}")
                                Text(text = "SĐT: ${employee.phone}")
                                Text(
                                    text = "${statusToStringEmploy(employee.status)}",
                                    color = Color.Red
                                )

                            }
                        }
                        Text(text = "Lương", fontSize = 15.sp)
                        Text(text = "Lương cơ bản:: ${employee.luongCb} VND")
                        Text(text = "Lương thưởng: ${employee.thuong} VND")
                        Text(text = "Tổng lương: ${total} VND")

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
                painter = painterResource(id = employee.image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = employee.name, fontSize = 14.sp, color = Color.Black)
                Text(text = employee.phone, fontSize = 14.sp, color = Color.Black)
                Text(text = "${statusToStringEmploy(employee.status)}", fontSize = 14.sp, color = Color.Black)
                Text(text = employee.job, fontSize = 14.sp, color = Color.Black)
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

@Composable
fun statusToStringEmploy(status: Int): String {
    return when (status) {
        0 -> "Ca Sáng"
        1 -> "Ca Tối"
        2 -> "Ca Đêm"
        else -> "Unknown"
    }
}
