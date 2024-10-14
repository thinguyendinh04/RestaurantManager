package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.Bill
import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.IngreCT
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.NguyenLieuItem
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.TableItem
import com.dinhthi2004.restaurantmanager.uilts.Route
import com.dinhthi2004.restaurantmanager.viewmodel.IngredientViewModel
import com.dinhthi2004.restaurantmanager.viewmodel.OrderViewModel
import com.dinhthi2004.restaurantmanager.viewmodel.TableViewModel

@Composable
fun HomeManager(navigationController: NavHostController) {
    val orderViewModel: OrderViewModel = viewModel()
    val token = TokenManager.token
    Log.d("token", "OrderViewModel: " + token)

    LaunchedEffect(Unit) {
        if (token != null) {
            orderViewModel.getBills(token)
        }
    }
    val bill by orderViewModel.bills.observeAsState(emptyList())
    val tableViewModel: TableViewModel = viewModel()
    var showDialog by remember { mutableStateOf(false) }
    var selectedOrder by remember { mutableStateOf<Bill?>(null) }
    val ingredientViewModel: IngredientViewModel = viewModel()
    LaunchedEffect(Unit) {
        token?.let { ingredientViewModel.getIngredients(it) }
    }
    LaunchedEffect(Unit) {
        token?.let { tableViewModel.getTables(it) }
    }
    val ingredients by ingredientViewModel.ingredients.observeAsState(emptyList())
    val tables by tableViewModel.tables.observeAsState(emptyList())
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
            items(tables) { table ->
                TableItem(table = table) { }
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
            items(bill) { order ->
                HomeBill(bill = order) {
                    selectedOrder = order
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
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
            items(ingredients) { ingredient ->
                NguyenLieuItem(ingredient = ingredient) {

                }
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
fun HomeBill(
    bill: Bill,
    onOrderSelected: (Bill) -> Unit
) {
    val textColor = when (bill.bill_status) {
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
                border = BorderStroke(1.dp, Color(0xff565E6C)),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
        ) {
            Text(
                text = "Mã bàn: ${bill.id_table}",
                fontSize = 12.sp,
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = "Tổng tiền: ${bill.total} VND",
                fontSize = 12.sp,
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = statusToString(bill.bill_status),
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



