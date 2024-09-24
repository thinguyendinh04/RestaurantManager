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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.dinhthi2004.restaurantmanager.uilts.Route

data class NguyenLieu(
    val id: Int,
    val name: String,
    val loai:String,
    val image: Int,
    val ton: Int,
    val nhap: Int,
    val price: Double
)

data class Table(
    val id: Int,
    val ban: String,
    val name: String,
    val phone: String,
    val quantity: Number,
    val descreption: String,
    val isOnline: Boolean
)

data class HoaDon(
    val id: Int,
    val banId: String,
    val price: Double,
    val items: List<Item>,
    val status: Int
){
    fun calculateTotalPrice(): Double {
        return items.sumOf { it.price }
    }
}

data class Item(val name: String,val image:Int, val quantity: Int, val price: Double)

val nguyen = listOf(
    NguyenLieu(1, "Súp Nơ","Rau", R.drawable.sup_no, 2, 3, 100.0),
    NguyenLieu(2, "Cua Cà Mau","Hải sản", R.drawable.cua, 2, 3, 100.0),
    NguyenLieu(3, "Tôm Hùm","Hải sản", R.drawable.alaska, 2, 3, 100.0),
    NguyenLieu(4, "Thịt Bò","Thịt", R.drawable.thit_bo, 2, 3, 100.0),
    NguyenLieu(6, "Thịt Lợn","Thịt", R.drawable.thit_lon, 2, 3, 100.0),
    NguyenLieu(9, "Súp Nơ","Rau", R.drawable.sup_no, 2, 3, 100.0),
    NguyenLieu(10, "Súp Nơ","Rau", R.drawable.sup_no, 2, 3, 100.0),
    NguyenLieu(5, "Thịt Lợn","Thịt", R.drawable.thit_lon, 2, 3, 100.0),
    NguyenLieu(7, "Thịt Lợn","Thịt", R.drawable.thit_lon, 2, 3, 100.0),
    NguyenLieu(8, "Giấy Ăn","Khác", R.drawable.paper, 2, 3, 100.0),
    NguyenLieu(12, "Giấy Ăn","Khác", R.drawable.paper, 2, 3, 100.0),
)
val bill = listOf(
    HoaDon(
        1, "Bàn 11", 256.000, items = listOf(
            Item(name = "Salat Cá Ngừ",R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Tôm Sú",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Cua Alaska",R.drawable.cua, quantity = 1, price = 10000.0)
        ), 1
    ),
    HoaDon(
        2, "Bàn 13", 256.000, items = listOf(
            Item(name = "Salat Cá Ngừ",R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Salat Cá Ngừ1",R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Salat Cá Ngừ2",R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Tôm Sú",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Cua Alaska",R.drawable.cua, quantity = 1, price = 10000.0)
        ), 0
    ),
    HoaDon(
        3, "Bàn 14", 256.000, items = listOf(
            Item(name = "Salat Cá Ngừ",R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Tôm Sú",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú1",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú2",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Cua Alaska",R.drawable.cua, quantity = 1, price = 10000.0)
        ), 1
    ),
    HoaDon(
        4, "Bàn 19", 256.000, items = listOf(
            Item(name = "Salat Cá Ngừ",R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Tôm Sú",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Cua Alaska",R.drawable.cua, quantity = 1, price = 10000.0),
            Item(name = "Cua Alaska1",R.drawable.cua, quantity = 3, price = 700.0),
            Item(name = "Cua Alaska3",R.drawable.cua, quantity = 3, price = 700.0)
        ), 0
    ),
    HoaDon(
        5, "Bàn 9", 256.000, items = listOf(
            Item(name = "Salat Cá Ngừ",R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Tôm Sú",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú4",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5",R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Cua Alaska",R.drawable.cua, quantity = 1, price = 10000.0)
        ), 1
    ),
    HoaDon(
            6, "Bàn 34", 256.000, items = listOf(
        Item(name = "Salat Cá Ngừ",R.drawable.cua, quantity = 2, price = 20000.0),
        Item(name = "Tôm Sú",R.drawable.cua, quantity = 2, price = 10000.0),
        Item(name = "Tôm Sú4",R.drawable.cua, quantity = 2, price = 10000.0),
        Item(name = "Tôm Sú5",R.drawable.cua, quantity = 2, price = 10000.0),
        Item(name = "Cua Alaska",R.drawable.cua, quantity = 1, price = 10000.0)
    ), 1
)
)
val items = listOf(
    Table(1, "12", "Nguyen B", "0975432178", 4, "không có", isOnline = false),
    Table(2, "13", "Nguyen C", "0975432179", 3, "Quán này ok", isOnline = true),
    Table(3, "15", "Nguyen V", "0975432174", 4, "", isOnline = true),
    Table(4, "14", "Nguyen D", "0975432175", 12, "tôi sẽ liên hệ với bạn sau", isOnline = false),
    Table(5, "11", "Nguyen T", "0975432176", 5, "giúp tôi tổ chức tiệc", isOnline = true)

)

@Composable
fun HomeManager(navigationController: NavHostController) {
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
        Row(
            modifier = Modifier
                .fillMaxWidth() // Chiếm toàn bộ chiều rộng
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween // Căn đều hai đầu
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
                .fillMaxWidth().padding(top = 10.dp)
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
                .fillMaxWidth().padding(top = 10.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.Center
        ) {
            items(bill.size) { index ->
                HomeBill(index, navigationController = navigationController){
                        order ->
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
                .fillMaxWidth().padding(top = 10.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.Center
        ) {
            items(nguyen.size) { index ->
                HomeNguyen(index)
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
                border = BorderStroke(1.dp, Color(0xff565E6C)), // Bo viền
                shape = RoundedCornerShape(8.dp) // Tạo bo góc nếu cần
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
fun HomeBill(index: Int,navigationController: NavHostController,onOrderSelected: (HoaDon) -> Unit) {
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
            .clickable { onOrderSelected(bill)}
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNguyen(index: Int) {

    val nguyen = nguyen[index]
    //Dialog CT
    var showDialog by remember { mutableStateOf(false) }
    val hangTonHomNay = nguyen.nhap - nguyen.ton
    val priceNguyen = nguyen.price * nguyen.nhap
    // Hiển thị dialog chi tiết khi người dùng nhấn vào
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            modifier = Modifier.clip(RoundedCornerShape(16.dp)), // Bo tròn toàn bộ dialog
            properties = DialogProperties(usePlatformDefaultWidth = false) // Không sử dụng chiều rộng mặc định của nền tảng
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 40.dp, start = 40.dp)
                    .clip(RoundedCornerShape(16.dp)) // Bo tròn toàn bộ Box
                    .background(Color.White) // Màu nền của dialog
            ) {
                Column {
                    // TopBar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color(0xFF7ffcff))
                            .padding(16.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "${nguyen.loai} ${nguyen.name}",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 20.sp,

                                ),
                            modifier = Modifier.align(Alignment.Center)
                        )

                    }

                    // Nội dung dialog
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
                        ) {
                            Image(
                                painter = painterResource(id = nguyen.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.LightGray),
                                contentScale = ContentScale.FillHeight
                            )

                            Spacer(modifier = Modifier.width(16.dp)) // Khoảng cách giữa ảnh và văn bản

                            Column {
                                Text(text = "Hàng nhập: ${nguyen.nhap} kg")
                                Text(text = "Hàng tồn: ${nguyen.ton} kg")
                                Text(text = "Hàng tồn hôm nay: $hangTonHomNay kg")
                                Row {
                                    Text(text = "Giá: ", color = Color.Black) // Màu đen cho "Giá: "
                                    Text(
                                        text = "${priceNguyen}k",
                                        color = Color.Red
                                    )
                                }
                            }
                        }
                    }
                    Button(
                        onClick = { showDialog = false },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp)), // Bo tròn cho nút
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7ffcff)) // Màu cho nút
                    ) {
                        Text("Đóng", color = Color.Black) // Màu chữ cho nút
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(115.dp)
            .padding(start = 10.dp)
            .clickable {showDialog=true }
            .border(
                border = BorderStroke(1.dp, Color.White), // Bo viền
                shape = RoundedCornerShape(8.dp) // Tạo bo góc nếu cần
            ),
        contentAlignment = Alignment.Center // Căn giữa toàn bộ nội dung trong Box
    ) {
        Column(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxSize(), // Đảm bảo Column chiếm toàn bộ chiều cao của Box
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Image(
                painter = painterResource(id = nguyen.image),
                contentDescription = "",
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .clip(shape = RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillHeight
            )
            Text(
                text = "Hàng nhập: ${nguyen.nhap} kg",
                fontSize = 12.sp,
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge,
            )

        }
    }
}


