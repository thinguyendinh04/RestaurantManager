package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.dinhthi2004.restaurantmanager.model.Meal

@Composable
fun DeleteConfirmationDialog(
    meal: Meal,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Xóa món ăn", style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Text(text = "Bạn có chắc chắn muốn xóa món ăn ${meal.name} không?")
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                }
            ) {
                Text("Xác nhận")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}