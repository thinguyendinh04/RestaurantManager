package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data.HoaDon
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.statusToString


@Composable
fun OrderItem(order: HoaDon, onOrderSelected: (HoaDon) -> Unit) {
    val textColor = when (order.status) {
        0 -> Color.Red
        1 -> Color.Green
        else -> Color.Gray
    }

    val itemsToShow = order.items.take(4)
    val totalPrice = order.calculateTotalPrice()

    Box(
        modifier = Modifier
            .width(170.dp)
            .height(146.dp)
            .padding(start = 10.dp)
            .clickable { onOrderSelected(order) }
            .border(
                border = BorderStroke(1.dp, Color(0xff565E6C)),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row {
                Text(text = "${order.banId} - ", fontSize = 14.sp, color = Color.Black)
                Text(text = "$totalPrice k", fontSize = 14.sp, color = Color(0xfffe763e))
            }

            itemsToShow.forEach { item ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "${item.name}: ${item.quantity}", fontSize = 12.sp)
                    Text(text = "${item.price}k", fontSize = 12.sp, color = Color(0xfffe763e))
                }
            }

            Text(text = statusToString(order.status), fontSize = 12.sp, color = textColor)
        }
    }
}
