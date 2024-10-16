package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Table

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
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
import coil.compose.AsyncImage
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.dish.Dish

@Composable
fun ItemOrderProduct(
    item: Dish,
    quantity: Int,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit
) {
    BoxWithConstraints {
        val screenWidth = maxWidth

        // Điều chỉnh kích thước dựa trên chiều rộng màn hình
        val imageSize = if (screenWidth < 400.dp) 40.dp else if (screenWidth < 600.dp) 60.dp else 80.dp
        val textSize = if (screenWidth < 400.dp) 10.sp else if (screenWidth < 600.dp) 12.sp else 14.sp
        val titleTextSize = if (screenWidth < 400.dp) 12.sp else if (screenWidth < 600.dp) 14.sp else 16.sp
        val cardPadding = if (screenWidth < 400.dp) 4.dp else 8.dp
        val elementSpacing = if (screenWidth < 400.dp) 4.dp else 8.dp

        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f) // Tự động chiếm 90% chiều rộng của màn hình
                .padding(cardPadding), // Padding tự động điều chỉnh
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(cardPadding), // Padding tự động cho nội dung bên trong
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = item.image_url,
                    contentDescription = item.name,
                    modifier = Modifier
                        .size(imageSize) // Kích thước ảnh điều chỉnh dựa trên kích thước màn hình
                        .padding(elementSpacing)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(elementSpacing)) // Khoảng cách giữa ảnh và text

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = titleTextSize // Kích thước font tự điều chỉnh
                    )
                    Text(
                        text = "${item.price} đ",
                        fontSize = textSize, // Kích thước font của giá tự điều chỉnh
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = { onDecreaseClick() },
                    enabled = quantity > 0
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_remove),
                        contentDescription = "Giảm"
                    )
                }

                Text(
                    text = "$quantity",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = elementSpacing) // Điều chỉnh padding của số lượng
                )

                IconButton(
                    onClick = { onIncreaseClick() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "Tăng"
                    )
                }
            }
        }
    }
}
