package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController

@Composable
fun IngreCT(navigationController: NavHostController, order: HoaDon?, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier.fillMaxWidth().height(600.dp).clip(RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {

                Column {
                    order?.let {
                        CustomTopBar(hoaDon = it)

                        Box(modifier = Modifier.height(350.dp)) { // Giới hạn chiều cao ở đây
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                            items(it.items) { item ->

                                // Hiển thị mỗi mặt hàng trong Row
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp), // Padding đều cho mỗi hàng
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Hiển thị hình ảnh ở bên trái
                                    Image(
                                        painter = painterResource(id = item.image),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(60.dp)
                                            .padding(start = 10.dp)
                                            .clip(CircleShape)
                                    )

                                    // Cột chứa tên và số lượng
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(start = 16.dp)
                                    ) {
                                        Text(
                                            text = item.name,
                                            style = MaterialTheme.typography.bodyMedium
                                        )

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(25.dp) // Kích thước Box chứa IconButton
                                                    .background(
                                                        Color(0xff2acccf),
                                                        shape = CircleShape
                                                    ) // Thêm màu nền và bo tròn
                                            ) {
                                                IconButton(
                                                    onClick = { /* Thêm logic tăng số lượng */ },
                                                    modifier = Modifier.size(25.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Remove, // Biểu tượng nút tăng
                                                        contentDescription = "Add",
                                                        tint = Color.Black, // Màu của biểu tượng
                                                        modifier = Modifier.size(15.dp) // Kích thước của biểu tượng
                                                    )
                                                }
                                            }

                                            Text(
                                                text = "${item.quantity}",
                                                style = MaterialTheme.typography.bodyMedium,
                                                modifier = Modifier.padding(horizontal = 8.dp)
                                            )

                                            Box(
                                                modifier = Modifier
                                                    .size(25.dp) // Kích thước Box chứa IconButton
                                                    .background(
                                                        Color(0xff2acccf),
                                                        shape = CircleShape
                                                    ) // Thêm màu nền và bo tròn
                                            ) {
                                                IconButton(
                                                    onClick = { /* Thêm logic tăng số lượng */ },
                                                    modifier = Modifier.size(25.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Add, // Biểu tượng nút tăng
                                                        contentDescription = "Add",
                                                        tint = Color.Black, // Màu của biểu tượng
                                                        modifier = Modifier.size(15.dp) // Kích thước của biểu tượng
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    // Hiển thị giá ở bên phải
                                    Text(
                                        text = "${item.price}k",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Red,
                                        modifier = Modifier.padding(end = 16.dp)
                                    )
                                }
                                Divider(thickness = 2.dp, color = Color(0xffBCC1CA))
                            }
                        }
                    }
                        Divider(thickness = 2.dp, color = Color(0xffBCC1CA))
                    }

                    Button(
                        onClick = {
                          onDismiss()
                        },
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff565e6c), // Màu nền của nút
                            contentColor = Color.White // Màu chữ
                        )
                    ) {
                        Text(text = "Đóng")
                    }
                }
            }
        }
    }
}

@Composable
fun CustomTopBar(hoaDon: HoaDon) {
    val textColor = when (hoaDon.status) {
        0 -> Color.Red
        1 -> Color.Green
        else -> Color.Gray
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xff2acccf)),
        shape = RoundedCornerShape(bottomStart = 140.dp, bottomEnd = 140.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Đơn hàng - ${hoaDon.banId}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Tổng: ",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "${hoaDon.calculateTotalPrice()}k",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Red,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
                Text(
                    text = "${statusToString(hoaDon.status)}",
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
