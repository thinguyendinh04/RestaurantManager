package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.screen.Table

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.Table.ItemOrderProduct
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.dataProduct

@Composable
fun AddItemsDialog(
    products: List<dataProduct>, // Danh sách sản phẩm
    onDismiss: () -> Unit, // Callback khi đóng dialog
    onAddItems: (Map<dataProduct, Int>) -> Unit // Callback khi thêm món
) {
    var quantities by remember { mutableStateOf(products.associateWith { 0 }.toMutableMap()) }
    val totalQuantity = quantities.values.sum()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Thêm món",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color(0xFFFF6D00),
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bàn 4 Tầng 1",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                ) {
                    items(products) { product ->
                        ItemOrderProduct(
                            item = product,
                            quantity = quantities[product] ?: 0,
                            onIncreaseClick = {
                                quantities = quantities.toMutableMap().apply {
                                    this[product] = (this[product] ?: 0) + 1
                                }
                            },
                            onDecreaseClick = {
                                if ((quantities[product] ?: 0) > 0) {
                                    quantities = quantities.toMutableMap().apply {
                                        this[product] = (this[product] ?: 0) - 1
                                    }
                                }
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Đóng", color = Color.Black)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { onAddItems(quantities) }, // Truyền số lượng món đã chọn
                    enabled = totalQuantity > 0,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCDD2)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Thêm món", color = Color(0xFFFF6D00))
                }
            }
        }
    )
}