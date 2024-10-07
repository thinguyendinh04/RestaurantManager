package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dinhthi2004.restaurantmanager.R

data class Employee(
    val id: Int,
    val name: String,
    val image: Int,
    val phone: String,
    val status: Int,
    val job: String,
    val luongCb:Double,
    val thuong:Double
)

val employ = listOf(
    Employee(1, "Nguyen Van C", R.drawable.employee_logo, "0975432178", 2, "Nhân Viên",1204.5,23.5),
    Employee(2, "Nguyen Van D", R.drawable.employee_logo, "0975432179", 1, "Nhân Viên",1114.2,222.3),
    Employee(3, "Nguyen Van F", R.drawable.employee_logo, "0975432170", 0, "Nhân Viên",3322.4,444.3),
    Employee(4, "Nguyen Van V", R.drawable.employee_logo, "0975432176", 2, "Nhân Viên",3344.4,444.5),
    Employee(5, "Nguyen Van T", R.drawable.employee_logo, "0975432174", 1, "Nhân Viên",999.777,444.7)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeEmployee(navigationController: NavHostController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Search bar container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Placeholder
                if (searchQuery.text.isEmpty()) {
                    Text(
                        text = "Tìm kiếm...",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 42.dp)
                    )
                }

                // Actual TextField
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { newText -> searchQuery = newText },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    )
                )
            }

            LazyColumn(
                modifier = Modifier
                    .height(500.dp) // Chiếm không gian còn lại
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .background(Color.White),
            ) {
                items(employ.size) { index ->
                    HomeEmploy(index)
                }
            }
        }

        // FloatingActionButton
        FloatingActionButton(
            onClick = { /* Handle click event here */ },

            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(top = 20.dp, end = 15.dp, bottom = 20.dp),
            containerColor = Color(0xff99FDFF)
        ) {
            Icon(imageVector = Icons.Default.PersonAdd, contentDescription = "Thêm")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeEmploy(index: Int) {
    val home = employ[index]
    var showDialog by remember { mutableStateOf(false) }
    val total=home.thuong + home.luongCb
    // Hiển thị dialog chi tiết khi người dùng nhấn vào
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            modifier = Modifier.clip(RoundedCornerShape(16.dp)),
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp, start = 30.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            ) {
                Column {

                    // Nội dung dialog
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = home.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.LightGray),
                                contentScale = ContentScale.FillHeight
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(text = "Họ tên: ${home.name}")
                                Text(text = "Chức vụ: ${home.job}")
                                Text(text = "SĐT: ${home.phone}")
                                Text(
                                    text = "${statusToStringEmploy(home.status)}",
                                    color = Color.Red
                                )

                            }
                        }
                        Text(text = "Lương", fontSize = 15.sp)
                        Text(text = "Lương cơ bản:: ${home.luongCb} VND")
                        Text(text = "Lương thưởng: ${home.thuong} VND")
                        Text(text = "Tổng lương: ${total} VND")

                    }
                    Button(
                        onClick = { showDialog = false },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                             shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff565e6c))
                    ) {
                        Text("Đóng", color = Color.Black)
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(10.dp)
            .clickable { }
            .border(
                border = BorderStroke(1.dp, Color(0xff565E6C)),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = home.name,
                    fontSize = 14.sp,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = home.phone,
                    fontSize = 14.sp,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${statusToStringEmploy(home.status)}",
                    fontSize = 14.sp,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = home.job,
                    fontSize = 14.sp,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            // Thêm cột chứa các icon ở bên phải
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)

            ) {
                IconButton(onClick = { showDialog=true }) {
                    Icon(imageVector = Icons.Default.Description, contentDescription = "Edit")
                }
                IconButton(onClick = { /* Handle click for the second icon */ }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Delete")
                }
            }
        }
    }
}


@Composable
fun statusToStringEmploy(status: Int): String {
    return when (status) {
        0 -> "Ca Sáng"
        1 -> "Ca Tối"
        2 -> "Ca Đêm"
        else -> "Unknown"
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeEmployee() {
    HomeEmployee(navigationController = rememberNavController())
}

