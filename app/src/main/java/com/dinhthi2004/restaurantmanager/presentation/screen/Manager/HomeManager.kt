package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.IngreCT
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.NguyenLieuItem
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data.HoaDon
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data.bill
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data.items
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data.nguyen
import com.dinhthi2004.restaurantmanager.uilts.Route



@Composable
fun HomeManager(navigationController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedOrder by remember { mutableStateOf<HoaDon?>(null) }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Bàn ăn",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                modifier = Modifier

            )
            Text(
                text = "Xem tất cả",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xff379AE6),
                modifier = Modifier.clickable {
                    navigationController.navigate(Route.HomeTable.screen)
                }

            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.Center
        ) {
            items(items.size) { index ->
                HomeItem(index)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Đơn hàng",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                modifier = Modifier

            )
            Text(
                text = "Xem tất cả",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xff379AE6),
                modifier = Modifier
                    .clickable {
                        navigationController.navigate(Route.HomeOrder.screen)
                    }
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.Center
        ) {
            items(bill.size) { index ->
                HomeBill(index, navigationController = navigationController) { order ->
                    selectedOrder = order
                    showDialog = true
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth() // Chiếm toàn bộ chiều rộng
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween // Căn đều hai đầu
        ) {

            Text(
                text = "Nguyên liệu",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                modifier = Modifier

            )
            Text(
                text = "Xem tất cả",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xff379AE6),
                modifier = Modifier
                    .clickable {
                        navigationController.navigate(Route.HomeIngredients.screen)
                    }
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.Center
        ) {
            items(nguyen.size) { index ->
                NguyenLieuItem(index){}
            }
        }
    }

    if (showDialog && selectedOrder != null) {
        IngreCT(navigationController, order = selectedOrder, onDismiss = {
            showDialog = false
            selectedOrder = null
        })
    }
}


@Composable
fun HomeItem(index: Int) {
    val home = items[index]
    val additionalInfo = if (home.isOnline) "" else " - Online"
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(80.dp)
            .padding(start = 10.dp)
            .clickable { }
            .border(
                border = BorderStroke(1.dp, Color(0xff565E6C)),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
        ) {
            Text(
                text = "Bàn " + home.ban + additionalInfo,
                fontSize = 12.sp,
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = home.name,
                fontSize = 12.sp,
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = home.phone,
                fontSize = 12.sp,
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun HomeBill(
    index: Int,
    navigationController: NavHostController,
    onOrderSelected: (HoaDon) -> Unit
) {
    val bill = bill[index]
    val itemsToShow = bill.items.take(3)
    val textColor = when (bill.status) {
        0 -> Color.Red
        1 -> Color.Green
        else -> Color.Gray
    }
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(120.dp)
            .padding(start = 10.dp)
            .clickable { onOrderSelected(bill) }
            .border(
                border = BorderStroke(1.dp, Color(0xff565E6C)), // Bo viền
                shape = RoundedCornerShape(8.dp) // Tạo bo góc nếu cần
            )
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
        ) {
            Text(
                text = bill.banId + " - " + bill.price,
                fontSize = 12.sp,
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge
            )
            itemsToShow.forEach { item ->
                Text(
                    text = "${item.name} : ${item.quantity}",
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Text(
                text = "${statusToString(bill.status)}",
                fontSize = 12.sp,
                color = textColor,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun statusToString(status: Int): String {
    return when (status) {
        0 -> "Chưa thanh toán"
        1 -> "Đã thanh toán"
        else -> "Unknown"
    }
}



