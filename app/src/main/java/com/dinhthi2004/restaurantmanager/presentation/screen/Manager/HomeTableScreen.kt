package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.R

import com.dinhthi2004.restaurantmanager.model.TokenManager
import com.dinhthi2004.restaurantmanager.model.table.Tabledata
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.NguyenLieuItem
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.AddIngredientDialog
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.DialogTable

import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.TableItem
import com.dinhthi2004.restaurantmanager.viewmodel.IngredientViewModel
import com.dinhthi2004.restaurantmanager.viewmodel.TableViewModel

@Composable
fun HomeTableScreen(navigationController: NavHostController) {
    val context= LocalContext.current
    val tableViewModel: TableViewModel = viewModel()
    val token=TokenManager.token
    LaunchedEffect(Unit) {
        if (token != null) {
            tableViewModel.getTables(token)
        }
    }

    // Khởi tạo tables với giá trị mặc định là một danh sách rỗng
    val tables by tableViewModel.tables.observeAsState(emptyList())
    var tableToDelete by remember { mutableStateOf<Tabledata?>(null) }

    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }


    if (showDialog) {
       DialogTable(onDismiss = { showDialog = false })
    }
    if (showDeleteConfirmDialog && tableToDelete != null) {
        // Dialog xác nhận xóa
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = false },
            title = { Text(text = "Xác nhận xóa") },
            text = { Text("Bạn có chắc chắn muốn xóa bàn ${tableToDelete?.table_name} không?") },
            confirmButton = {
                Button(
                    onClick = {
                        // Gọi hàm xóa bàn
                        tableToDelete?.let { table ->
                            table.id?.let {
                                token?.let { it1 ->
                                    tableViewModel.deleteTable(it1, it) {
                                        tableViewModel.getTables(token) // Cập nhật lại danh sách bàn
                                    }
                                }
                            }
                        }
                        showDeleteConfirmDialog = false
                        Toast.makeText(context,"Xóa thành công", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text("Xóa")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteConfirmDialog = false }) {
                    Text("Hủy")
                }
            }
        )
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


        if (tables != null && tables.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = Modifier.height(320.dp),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tables) { table ->
                    TableItem(table = table) {
                        tableToDelete = table
                        showDeleteConfirmDialog = true
                    }
                }
            }
        } else {
            // Hiển thị thông báo khi không có bàn nào
            Text(
                text = "Không có bàn nào.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
            )
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

