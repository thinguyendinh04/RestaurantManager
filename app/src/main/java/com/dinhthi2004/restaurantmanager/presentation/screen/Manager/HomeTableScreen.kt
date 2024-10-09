package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.AddIngredientDialog
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.DialogTable
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.TableItem
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data.items

@Composable
fun HomeTableScreen(navigationController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }


    if (showDialog) {
       DialogTable(onDismiss = { showDialog = false })
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
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
                TableItem(items[index])
            }
        }
        Button(
            onClick = { showDialog=true },
            modifier = Modifier
                .padding(top = 5.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff2acccf),
                contentColor = Color.White
            )
        ) {
            Text(text = "Thêm Bàn Ăn Mới")
        }
    }
}
