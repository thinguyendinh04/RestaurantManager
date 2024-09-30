package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CategoryProduct(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val categoriesProduct = listOf("Tất cả", "Best seller", "Hải sản", "Khác")
        items(categoriesProduct) { category ->
            val isSelected = category == selectedCategory

            Button(
                onClick = { onCategorySelected(category) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color.Black else Color.Gray,
                    contentColor = if (isSelected) Color.White else Color.Black
                )
            ) {
                Text(text = category)
            }
        }
    }
}