package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberImagePainter
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.Ingredient

import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data.nguyen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NguyenLieuItem(ingredient: Ingredient, onItemClicked: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }


    // Hiển thị dialog chi tiết khi người dùng nhấn vào
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            modifier = Modifier.clip(RoundedCornerShape(16.dp)),
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            // Nội dung của dialog
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 50.dp, start = 50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            ) {
                Column {
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
                            text = "${ingredient.ingredient_name}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ingre),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.LightGray),
                                contentScale = ContentScale.FillHeight
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = "Số lượng: ${ingredient.ingredients_amount}")
                            }
                        }
                    }
                    Button(
                        onClick = { showDialog = false },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7ffcff))
                    ) {
                        Text("Đóng", color = Color.Black)
                    }
                }
            }
        }
    }

    // Giao diện hiển thị nguyên liệu
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(115.dp)
            .padding(start = 10.dp)
            .clickable { showDialog = true }
            .border(border = BorderStroke(1.dp, Color.White), shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter =painterResource(R.drawable.ingre),
                contentDescription = "",
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .clip(shape = RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillHeight
            )
            Text(
                text = "Số lượng: ${ingredient.ingredients_amount}",
                fontSize = 12.sp,
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

