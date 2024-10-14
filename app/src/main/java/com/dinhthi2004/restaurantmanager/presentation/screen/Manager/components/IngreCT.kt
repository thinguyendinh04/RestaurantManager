package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.model.Bill
import com.dinhthi2004.restaurantmanager.model.BillDetail
import com.dinhthi2004.restaurantmanager.model.Order.OrderData
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.model.bill.BillData
import com.dinhthi2004.restaurantmanager.model.dish.Dish
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.statusToString
import com.dinhthi2004.restaurantmanager.viewmodel.DishViewModel
import com.dinhthi2004.restaurantmanager.viewmodel.OrderViewModel

@Composable
fun IngreCT(
    navigationController: NavHostController,
    billData: BillData?, // Dữ liệu hóa đơn
    onDismiss: () -> Unit
) {
    val orderViewModel: OrderViewModel = viewModel()
    val dishViewModel: DishViewModel = viewModel()
    val orderDetail by orderViewModel.order.observeAsState(null) // Quan sát dữ liệu chi tiết đơn hàng
    val dishDetail by dishViewModel.dish.observeAsState(null) // Quan sát dữ liệu chi tiết món ăn
    val token = TokenManager.token

    // Lấy chi tiết đơn hàng
    LaunchedEffect(billData?.order_id) {
        if (token != null && billData?.order_id != null) {
            orderViewModel.getOrderDetail(token, billData.order_id) // Gọi API lấy chi tiết đơn hàng
        }
    }

    // Lấy chi tiết món ăn khi có đơn hàng và `dish_id`
    LaunchedEffect(orderDetail) {
        val dishId = orderDetail?.dish_id
        if (token != null && dishId != null) {
            dishViewModel.getDishDetail(token, dishId) // Gọi API lấy chi tiết món ăn
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Hiển thị chi tiết hóa đơn
                    billData?.let {
                        CustomTopBar(it)
                    }



                    // Hiển thị chi tiết đơn hàng (order)
                    orderDetail?.let { order ->
                        Text(text = "Chi tiết đơn hàng:")

                        // Hiển thị thông tin món ăn nếu có
                        if (dishDetail != null) {
                            Text(text = "Tên món: ${dishDetail!!.name}")
                            Text(text = "Giá: ${dishDetail!!.price}k")
                        } else {
                            Text(text = "Món ăn không có thông tin.")
                        }

                        // Hiển thị số lượng
                        Text(text = "Số lượng: ${order.amount}")
                    } ?: run {
                        Text(text = "Không có thông tin đơn hàng.")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff565e6c),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Đóng")
                    }
                }
            }
        }
    }
}



@Composable
fun CustomTopBar(billData: BillData) {
    val textColor = when (billData.status) {
        0 -> Color.Red
        1 -> Color.Green
        else -> Color.Gray
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xff2acccf)),
        shape = RoundedCornerShape(bottomStart = 140.dp, bottomEnd = 140.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ID: ${billData.id ?: "N/A"}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Tổng tiền: ",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "${billData.tong_tien}k",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Red,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
                Text(
                    text = statusToString(billData.status),
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
