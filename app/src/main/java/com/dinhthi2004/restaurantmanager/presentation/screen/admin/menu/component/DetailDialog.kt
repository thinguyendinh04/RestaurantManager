package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.dinhthi2004.restaurantmanager.model.Meal

@Composable
fun MealDetailDialog(meal: Meal, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Chi tiết món ăn", style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Column {
                Text(text = "Tên: ${meal.name}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Giá: ${meal.price} VND", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Trạng thái: ${if (meal.status == 1) "Còn hàng" else "Hết hàng"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = "Thông tin: ${meal.info}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Đánh giá: ${meal.rating}", style = MaterialTheme.typography.bodyMedium)
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Đóng")
            }
        }
    )
}