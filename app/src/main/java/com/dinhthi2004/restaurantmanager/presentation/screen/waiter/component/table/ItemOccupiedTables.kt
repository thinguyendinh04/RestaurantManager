package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component.table
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Table(
    val name: String,
    val position: String,
    val maxCapacity: Int,
    val currentCapacity: Int,
    val dishList: List<String>,
    val totalAmount: Double,
    val isPaid: Boolean
)

@Composable
fun InUseTables() {
    var selectedTable by remember { mutableStateOf<Table?>(null) }

    // Sample list of tables in use
    val tablesInUse = listOf(
        Table("Bàn 1", "Tầng 1", 6, 4, listOf("Món A", "Món B"), 500000.0, false),
        Table("Bàn 2", "Tầng 2", 4, 4, listOf("Món C", "Món D"), 300000.0, true),
        Table("Bàn 3", "Tầng 1", 8, 7, listOf("Món E", "Món F", "Món G"), 700000.0, false)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        LazyColumn {
            items(tablesInUse.size) { index ->
                TableIconView(
                    table = tablesInUse[index],
                    onTableClick = { selectedTable = tablesInUse[index] }
                )
            }
        }

        selectedTable?.let {
            TableDetailView(table = it, onPayClick = { selectedTable = null })
        }
    }
}

@Composable
fun TableIconView(table: Table, onTableClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable { onTableClick() }
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = table.name, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Sức chứa hiện tại: ${table.currentCapacity}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Tổng tiền: ${table.totalAmount} VND")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Trạng thái: ${if (table.isPaid) "Đã thanh toán" else "Chưa thanh toán"}")
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Add payment logic here */ },
                enabled = !table.isPaid
            ) {
                Text("Thanh toán")
            }
        }
    }
}

@Composable
fun TableDetailView(table: Table, onPayClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Tên bàn: ${table.name}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Vị trí: ${table.position}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Sức chứa tối đa: ${table.maxCapacity}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Sức chứa hiện tại: ${table.currentCapacity}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Danh sách món: ${table.dishList.joinToString()}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Tổng tiền: ${table.totalAmount} VND")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Trạng thái thanh toán: ${if (table.isPaid) "Đã thanh toán" else "Chưa thanh toán"}")
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onPayClick,
                enabled = !table.isPaid
            ) {
                Text("Thanh toán")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InUseTablesPreview() {
    InUseTables()
}
