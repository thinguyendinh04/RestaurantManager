package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dinhthi2004.restaurantmanager.uilts.Route

@Composable
fun HomeSetting(navigationController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "Manager",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Thêm 8 dòng Text bên trong Column với khoảng cách 10.dp
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Chung", color=Color(0xffbcc1ca),fontSize = 14.sp, modifier = Modifier.padding(vertical = 5.dp))
        Divider(thickness = 2.dp, color = Color(0xffBCC1CA))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Chỉnh sửa thông tin", fontSize = 14.sp, modifier = Modifier.padding(vertical = 5.dp).clickable { navigationController.navigate(Route.Setting.screen) })
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Quản lí nhân viên", fontSize = 14.sp, modifier = Modifier.padding(vertical = 5.dp).clickable {  navigationController.navigate(Route.HomeEmployScreen.screen)})
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Notification", fontSize = 14.sp, modifier = Modifier.padding(vertical = 5.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Hỗ trợ", fontSize = 14.sp, modifier = Modifier.padding(vertical = 5.dp))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Khác",color=Color(0xffbcc1ca), fontSize = 14.sp, modifier = Modifier.padding(vertical = 5.dp))
        Divider(thickness = 2.dp, color = Color(0xffBCC1CA))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "About", fontSize = 14.sp, modifier = Modifier.padding(vertical = 5.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Dark Mode", fontSize = 14.sp, modifier = Modifier.padding(vertical = 5.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Log out",color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(vertical = 5.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeSetting() {
    HomeSetting(navigationController = rememberNavController())
}
