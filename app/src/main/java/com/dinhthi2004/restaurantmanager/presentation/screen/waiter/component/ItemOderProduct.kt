package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.dataProduct

@Composable
fun ItemOrderProduct(
    item: dataProduct,
    quantity: Int, // Tham số để nhận số lượng hiện tại
    onIncreaseClick: () -> Unit, // Callback khi tăng số lượng
    onDecreaseClick: () -> Unit, // Callback khi giảm số lượng
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ảnh sản phẩm
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Tên: ${item.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Giá: ${item.price}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Số lượng: $quantity", // Hiển thị số lượng
                    fontSize = 14.sp
                )
            }

            // Nút giảm số lượng
            IconButton(
                onClick = { onDecreaseClick() },
                enabled = quantity > 0 // Ngăn không cho giảm khi số lượng bằng 0
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_remove), // Thay thế với icon giảm
                    contentDescription = "Decrease Quantity"
                )
            }

            // Nút tăng số lượng
            IconButton(
                onClick = { onIncreaseClick() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add), // Thay thế với icon tăng
                    contentDescription = "Increase Quantity"
                )
            }
        }
    }
}