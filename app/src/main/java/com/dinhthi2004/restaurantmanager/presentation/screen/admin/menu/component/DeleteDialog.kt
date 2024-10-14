package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.dinhthi2004.restaurantmanager.model.dish.Dish

@Composable
fun DeleteConfirmationDialog(
    dish: Dish,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Xóa món ăn", style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Text(text = "Bạn có chắc chắn muốn xóa món ăn ${dish.name} không?")
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    Toast.makeText(context, "Đã xóa món ăn ${dish.name}", Toast.LENGTH_SHORT).show()
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