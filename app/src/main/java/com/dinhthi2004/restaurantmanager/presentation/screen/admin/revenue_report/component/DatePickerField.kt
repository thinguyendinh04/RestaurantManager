package com.dinhthi2004.restaurantmanager.presentation.screen.admin.revenue_report.component

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dinhthi2004.restaurantmanager.R
import java.util.*

@Composable
fun DatePickerField(label: String, date: String?, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val selectedDate =
                String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            onDateSelected(selectedDate)
        },
        year,
        month,
        day
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = date ?: "Chưa chọn",
                modifier = Modifier.padding(end = 8.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = "Select Date",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { datePickerDialog.show() }
            )
        }
    }
}