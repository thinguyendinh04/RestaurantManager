package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun HomeRevenue(navigationController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Card(
                border = BorderStroke(1.5.dp, Color(0xff99fdff)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(40.dp)
                    .padding(start = 30.dp, end = 40.dp)
                    .fillMaxWidth(0.3f),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

                ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize().background(Color.White)
                ) {
                    Text(
                        text = "Từ Ngày",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black

                        )
                }
            }

            Card(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.5.dp, Color(0xff99fdff)),
                modifier = Modifier
                    .height(40.dp)
                    .padding(end = 20.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

                ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize().background(Color.White)
                ) {
                    Text(
                        text = "03/03/2024",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.fillMaxSize().padding(top = 10.dp, start = 10.dp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Card(
                border = BorderStroke(1.5.dp, Color(0xff99fdff)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(40.dp)
                    .padding(start = 30.dp, end = 40.dp)
                    .fillMaxWidth(0.3f),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

                ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize().background(Color.White)
                ) {
                    Text(
                        text = "Đến Ngày",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,

                        )
                }
            }
            Card(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.5.dp, Color(0xff99fdff)),
                modifier = Modifier
                    .height(40.dp)
                    .padding(end = 20.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

                ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize().background(Color.White)
                ) {
                    Text(
                        text = "03/03/2024",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.fillMaxSize().padding(top = 10.dp, start = 10.dp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp, start = 30.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Doanh Thu: ",
                fontSize = 20.sp,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = "99999k",
                fontSize = 20.sp,
                style = MaterialTheme.typography.labelLarge,
                color = Color(0xfffe763e),
                modifier = Modifier.padding(end = 10.dp)
            )
        }
        Button(
            onClick = {

            },
            modifier = Modifier.width(120.dp)
                .padding(top = 40.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff99fdff), // Màu nền của nút
                contentColor = Color.Black // Màu chữ
            )
        ) {
            Text(text = "Thống Kê")
        }
        Image(
            painter = painterResource(id = R.drawable.label),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(420.dp)
                .height(300.dp)
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(13.dp))
        )
    }
}
@Preview(showBackground = true)
@Composable
fun HomePreRevenue() {
    HomeRevenue(navigationController = rememberNavController())
}