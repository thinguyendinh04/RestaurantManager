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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun HomeTable(navigationController: NavHostController) {
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
            text = "Danh Sách Bàn Ăn",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Start).padding(start = 10.dp, top = 5.dp)
        )
        LazyVerticalGrid(
            modifier = Modifier.height(320.dp),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items.size) { index ->
                HomeTableCT(index)
            }
        }
        Button(
            onClick = {
                /* Thêm hành động khi nhấn nút */
            },
            modifier = Modifier
                .padding(top = 5.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff2acccf), // Màu nền của nút
                contentColor = Color.White // Màu chữ
            )
        ) {
            Text(text = "Thêm Bàn Ăn Mới")
        }
    }
}

@Composable
fun HomeTableCT(index: Int) {
    val table = items[index]
    val additionalInfo = if (table.isOnline) "" else " - Online"
    Box(
        modifier = Modifier
            .width(147.dp)
            .height(146.dp)
            .padding(start = 10.dp)
            .clickable { }
            .border(
                border = BorderStroke(1.dp, Color(0xff565E6C)), // Bo viền
                shape = RoundedCornerShape(8.dp) // Tạo bo góc nếu cần
            )
    ) {
        // Cột chứa các thông tin của bàn
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 10.dp, top = 5.dp)// Canh các Text phía trên bên trái
        ) {
            Text(
                text = "Bàn " + table.ban + additionalInfo,
                fontSize = 14.sp,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = table.name,
                fontSize = 14.sp,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = table.phone,
                fontSize = 14.sp,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Bàn ${table.quantity} người",
                fontSize = 14.sp,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = table.descreption,
                fontSize = 14.sp,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
        }
        IconButton(
            onClick = { /* Thêm logic khi nhấn vào icon */ },
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.EditNote, // Thay thế bằng icon mong muốn
                contentDescription = "Favorite Icon",
                modifier = Modifier.size(35.dp),
                tint = Color(0xff2acccf) // Màu của icon
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomePreView() {
    HomeTable(navigationController = rememberNavController())
}
