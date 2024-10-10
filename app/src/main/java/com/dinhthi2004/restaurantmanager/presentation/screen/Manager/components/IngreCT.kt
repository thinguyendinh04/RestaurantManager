package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.model.Bill
import com.dinhthi2004.restaurantmanager.model.BillDetail
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.statusToString

@Composable
fun IngreCT(
    navigationController: NavHostController, order: Bill?,onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
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
                    order?.let {
                        CustomTopBar(it)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            onDismiss()
                        },
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff565e6c), // Màu nền của nút
                            contentColor = Color.White // Màu chữ
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
fun CustomTopBar(hoaDon: Bill) {
    val textColor = when (hoaDon.bill_status) {
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
                    text = "Mã bàn - ${hoaDon.id_table ?: "N/A"}",
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
                        text = "${hoaDon.total}k",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Red,
                        modifier = Modifier.padding(end = 10.dp)
                    )

                }
                Text(
                    text = statusToString(hoaDon.bill_status),
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
