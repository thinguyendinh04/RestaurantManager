package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Oder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OrderCard(
    orderNumber: String,
    address: String,
    time: String,
    imageUrl: String,
    onCompleteClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Số thứ tự
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = orderNumber.takeLast(2),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Thông tin đơn hàng
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "#$orderNumber",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Địa chỉ: $address",
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                }

                // Nút chi tiết
                TextButton(onClick = onDetailClick) {
                    Text(
                        text = "Chi tiết",
                        color = Color(0xFFFFA726),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Hình ảnh món ăn
                Image(
                    painter = rememberImagePainter(data = imageUrl),
                    contentDescription = "Food Image",
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Gray, shape = CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Thời gian
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_lock_idle_alarm),
                        contentDescription = "Time Icon",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = time,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Nút hủy
                OutlinedButton(
                    onClick = onCancelClick,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(text = "Hủy", color = Color.Gray)
                }

                // Nút hoàn thành
                Button(
                    onClick = onCompleteClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text(text = "Hoàn thành", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderCard() {
    OrderCard(
        orderNumber = "0002",
        address = "Số 2 - Mỹ Đình 1 - Nam Từ Liêm",
        time = "09:30 AM",
        imageUrl = "https://example.com/food.jpg", // Replace with actual image URL or resource
        onCompleteClick = { /* TODO */ },
        onCancelClick = { /* TODO */ },
        onDetailClick = { /* TODO */ }
    )
}
