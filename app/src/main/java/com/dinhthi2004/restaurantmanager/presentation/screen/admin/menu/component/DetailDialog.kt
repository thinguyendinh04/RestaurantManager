package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.dish.Dish

@Composable
fun MealDetailDialog(
    dish: Dish, onDismiss: () -> Unit
) {
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
                    painter = painterResource(id = R.drawable.ic_dish),
                    contentDescription = "Meal image",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally)
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
            Button(
                onClick = {
                   onDismiss()
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Close")
            }
        }
    )
}

