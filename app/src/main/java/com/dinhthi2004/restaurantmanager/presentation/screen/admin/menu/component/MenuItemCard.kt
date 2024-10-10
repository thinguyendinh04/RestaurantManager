package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dinhthi2004.restaurantmanager.model.Meal

@Composable
fun MenuItemCard(meal: Meal, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        color = Color.LightGray,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Tên: ${meal.name}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                )
                Text(
                    text = "Giá: ${meal.price} VND",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                )
            }
        }
    }
}