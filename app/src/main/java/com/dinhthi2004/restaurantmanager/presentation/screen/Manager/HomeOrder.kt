package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dinhthi2004.restaurantmanager.R

@Composable
fun HomeOrder(navigationController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedOrder by remember { mutableStateOf<HoaDon?>(null) }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(380.dp)
                .height(200.dp)
                .padding(top = 5.dp)
                .clip(RoundedCornerShape(13.dp))
        )
        Text(
            text = "Danh Sách Đơn Hàng",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 10.dp, top = 5.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(bill.size) { index ->
                HomeOD(index, navigationController = navigationController){
                    order ->
                    selectedOrder = order
                    showDialog = true
                }
            }
        }
    }
    // Hiển thị Dialog nếu có đơn hàng được chọn
    if (showDialog && selectedOrder != null) {
        IngreCT(navigationController, order = selectedOrder, onDismiss = {
            showDialog = false
            selectedOrder = null // Đặt lại trạng thái
        })
    }
}

@Composable
fun HomeOD(index: Int, navigationController: NavHostController,onOrderSelected: (HoaDon) -> Unit) {

    val order = bill[index]
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
            .clickable { onOrderSelected(order)}
            .border(
                border = BorderStroke(1.dp, Color(0xff565E6C)),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        // Cột chứa các thông tin của bàn
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 10.dp, top = 5.dp)
        ) {
            // Sử dụng Row cho bàn và tổng giá
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "${order.banId}  - ",
                    fontSize = 14.sp,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${totalPrice}k",
                    fontSize = 14.sp,
                    color = Color(0xfffe763e),
                    modifier = Modifier.padding(end = 35.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            itemsToShow.forEach { item ->
                // Sử dụng Row cho tên và giá của item
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${item.name} : ${item.quantity}",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "${item.price}k",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xfffe763e),
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
            }
            Text(
                text = "${statusToString(order.status)}",
                fontSize = 12.sp,
                color = textColor,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomePreViewOrder() {
    HomeOrder(navigationController = rememberNavController())
}
