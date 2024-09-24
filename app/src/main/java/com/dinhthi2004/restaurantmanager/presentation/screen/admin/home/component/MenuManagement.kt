package com.dinhthi2004.restaurantmanager.presentation.screen.admin.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dinhthi2004.restaurantmanager.R

@Composable
fun MenuManagement(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicText(
                text = "Menu Management",
                modifier = Modifier.padding(start = 10.dp),
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp)
            )

            Text(
                text = "Show all",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable {
                        navController.navigate("menu_management_admin")
                    },
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Blue
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(3) {
                MenuItem(
                    name = "Tôm nướng phô mai",
                    price = "1.200.000 đ",
                    imageUrl = "http://"
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MenuItem(name: String, price: String, imageUrl: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = Color.LightGray,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                BasicText(
                    text = "Tên: $name",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                )
                BasicText(
                    text = "Giá: $price",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                )
            }
        }
    }
}