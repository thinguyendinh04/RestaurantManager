package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.dinhthi2004.restaurantmanager.model.dish.Dish

@Composable
fun MealDetailDialog(dish: Dish, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Chi tiết món ăn",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column {
                Image(
                    painter = rememberImagePainter(data = dish.image_url),
                    contentDescription = "Meal image",
                    modifier = Modifier.size(64.dp)
                )
                Text(text = "Tên: ${dish.name}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Giá: ${dish.price} VND", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Trạng thái: ${dish.status}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Thông tin: ${dish.information}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Đánh giá: ${dish.id_type}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Đóng")
            }
        }
    )
}