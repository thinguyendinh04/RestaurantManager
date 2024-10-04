package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.uilts.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setting(navigationController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    Icons.Default.ArrowBackIosNew,
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                        .clickable { navigationController.navigate(Route.HomeSetting.screen) },
                    tint = Color.Black
                )
            }
            Text(
                text = "Sửa hồ sơ",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .background(Color.White)
        ) {
            Card(
                modifier = Modifier
                    .padding(top = 20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
                border = BorderStroke(1.dp, color = Color(0xff99fdff))
            ) {
                Column(modifier = Modifier
                    .padding(top = 80.dp)
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState())) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(text = "Họ và Tên", fontSize = 17.sp, color = Color.Black)
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White
                            ),
                            value = "",
                            onValueChange = {  },
                            label = { Text("") }
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(text = "Email", fontSize = 17.sp, color = Color.Black)
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White
                            ),
                            value = "",
                            onValueChange = {  },
                            label = { Text("") }
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(text = "Số điện thoại", fontSize = 17.sp, color = Color.Black)
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White
                            ),
                            value = "",
                            onValueChange = {  },
                            label = { Text("") }
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(text = "Mật khẩu", fontSize = 17.sp, color = Color.Black)
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White
                            ),
                            value = "",
                            onValueChange = {  },
                            label = { Text("") }
                        )
                    }
                }
            }
            Box(
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.man),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .clip(CircleShape)

                )
                Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                    IconButton(
                        onClick = {  },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .size(27.dp)
                    ) {
                        Icon(imageVector = Icons.Rounded.PhotoCamera, contentDescription = "", modifier = Modifier.size(15.dp))
                    }
                }
            }
        }

        Divider(thickness = 1.dp, color = Color(0xffBCC1CA))
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFE724C)
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(0.5f)

        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Lưu",
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingPreview() {
    Setting(navigationController = rememberNavController())
}
