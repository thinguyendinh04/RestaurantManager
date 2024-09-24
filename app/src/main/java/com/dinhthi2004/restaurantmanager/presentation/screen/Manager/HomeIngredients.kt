package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun HomeIngredients(navigationController: NavHostController) {
    val loaiList = listOf("Tất cả") + nguyen.map { it.loai }.distinct()
    var selectedLoai by remember { mutableStateOf(loaiList.firstOrNull() ?: "") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            LazyRow(modifier = Modifier.padding(8.dp)) {
                items(loaiList) { loai ->
                    val isSelected = selectedLoai == loai
                    Button(
                        onClick = { selectedLoai = loai },
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) Color(0xff697183) else Color(0xffe5eeff)
                        )
                    ) {
                        Text(
                            text = loai,
                            color = if (isSelected) Color.White else Color(0xff002c7d) // Màu chữ cho nút được chọn và chưa chọn
                        )
                    }
                }
            }
            LazyVerticalGrid(
                modifier = Modifier.height(470.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(nguyen.filter {
                    selectedLoai == "Tất cả" || it.loai == selectedLoai
                }) { nguyenlieu ->
                    NguyenLieuItem(nguyenlieu)
                }
            }
        }

        IconButton(
            onClick = { /* Thêm logic khi nhấn vào icon */ },
            modifier = Modifier
                .align(Alignment.BottomEnd) // Đặt nút ở góc dưới bên phải
                .padding(end = 17.dp) // Thêm padding cho nút
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Icon",
                modifier = Modifier.size(45.dp),
                tint = Color.Black // Màu của icon
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NguyenLieuItem(nguyenLieu: NguyenLieu) {
    //Dialog CT
    var showDialog by remember { mutableStateOf(false) }
    val hangTonHomNay = nguyenLieu.nhap - nguyenLieu.ton
    val priceNguyen = nguyenLieu.price * nguyenLieu.nhap
    // Hiển thị dialog chi tiết khi người dùng nhấn vào
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            modifier = Modifier.clip(RoundedCornerShape(16.dp)), // Bo tròn toàn bộ dialog
            properties = DialogProperties(usePlatformDefaultWidth = false) // Không sử dụng chiều rộng mặc định của nền tảng
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 50.dp, start = 50.dp)
                    .clip(RoundedCornerShape(16.dp)) // Bo tròn toàn bộ Box
                    .background(Color.White) // Màu nền của dialog
            ) {
                Column {
                    // TopBar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color(0xFF7ffcff))
                            .padding(16.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "${nguyenLieu.loai} ${nguyenLieu.name}",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 20.sp,

                                ),
                            modifier = Modifier.align(Alignment.Center)
                        )

                    }

                    // Nội dung dialog
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
                        ) {
                            Image(
                                painter = painterResource(id = nguyenLieu.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.LightGray),
                                contentScale = ContentScale.FillHeight
                            )

                            Spacer(modifier = Modifier.width(16.dp)) // Khoảng cách giữa ảnh và văn bản

                            Column {
                                Text(text = "Hàng nhập: ${nguyenLieu.nhap} kg")
                                Text(text = "Hàng tồn: ${nguyenLieu.ton} kg")
                                Text(text = "Hàng tồn hôm nay: $hangTonHomNay kg")
                                Row {
                                    Text(text = "Giá: ", color = Color.Black) // Màu đen cho "Giá: "
                                    Text(
                                        text = "${priceNguyen}k",
                                        color = Color.Red
                                    )
                                }
                            }
                        }
                    }
                    Button(
                        onClick = { showDialog = false },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp)), // Bo tròn cho nút
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7ffcff)) // Màu cho nút
                    ) {
                        Text("Đóng", color = Color.Black) // Màu chữ cho nút
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(115.dp)
            .padding(start = 10.dp)
            .clickable { showDialog = true }
            .border(
                border = BorderStroke(1.dp, Color.White), // Bo viền
                shape = RoundedCornerShape(8.dp) // Tạo bo góc nếu cần
            ),
        contentAlignment = Alignment.Center // Căn giữa toàn bộ nội dung trong Box
    ) {
        Column(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxSize(), // Đảm bảo Column chiếm toàn bộ chiều cao của Box
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Image(
                painter = painterResource(id = nguyenLieu.image),
                contentDescription = "",
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .clip(shape = RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillHeight
            )
            Text(
                text = "Hàng nhập: ${nguyenLieu.nhap} kg",
                fontSize = 12.sp,
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = "Hàng tồn: ${nguyenLieu.ton} kg",
                fontSize = 12.sp,
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge,
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreViewIngre() {
    HomeIngredients(navigationController = rememberNavController())
}