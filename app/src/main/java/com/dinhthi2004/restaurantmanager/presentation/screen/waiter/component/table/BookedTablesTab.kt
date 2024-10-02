package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.table

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.Table

@Composable
fun BookedTablesTab(tables: List<Table>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(tables) { table ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Tên bàn: ${table.tableName}")
                    Text("Tên người đặt: ${table.bookerName}")
                    Text("Số điện thoại: ${table.bookerPhone}")
                    Text("Thời gian đặt: ${table.bookingTime}")
                    Text("Tiền cọc: ${table.depositAmount} VND")
                    Text("Vị trí: ${table.location}")
                    Text("Sức chứa: ${table.capacity}")
                }
            }
        }
    }
}