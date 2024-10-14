package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.OrderItem
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.Table

// Danh sách hóa đơn tạm thời (lưu trong bộ nhớ)
val invoices = mutableListOf<Invoice>()

// Định nghĩa dữ liệu hóa đơn
data class Invoice(
    val invoiceId: Int,
    val tableName: String,
    val orderList: List<OrderItem>,
    val totalAmount: Double
)


@Composable
fun InvoiceList() {
    val invoiceList = remember { invoices.toList() }  // Dùng remember để lưu danh sách hóa đơn

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(invoiceList) { invoice ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(text = "Hóa đơn #${invoice.invoiceId}")
                Text(text = "Bàn: ${invoice.tableName}")
                Text(text = "Tổng tiền: ${invoice.totalAmount} đ")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
