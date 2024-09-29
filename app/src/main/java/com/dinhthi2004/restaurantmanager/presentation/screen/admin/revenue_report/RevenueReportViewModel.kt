package com.dinhthi2004.restaurantmanager.presentation.screen.admin.revenue_report

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.revenue_report.model.Revenue
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.revenue_report.model.RevenueReportData

class RevenueReportViewModel : ViewModel() {
    var startDate by mutableStateOf<String?>(null)
        private set

    var endDate by mutableStateOf<String?>(null)
        private set

    var reportData by mutableStateOf<RevenueReportData?>(null)
        private set

    fun onStartDateSelected(selectedDate: String) {
        startDate = selectedDate
    }

    fun onEndDateSelected(selectedDate: String) {
        endDate = selectedDate
    }

    fun generateReport() {
        if (startDate != null && endDate != null) {
            reportData = RevenueReportData(
                startDate = startDate!!,
                endDate = endDate!!,
                totalRevenue = "49.000.000 đ",
                revenues = listOf(
                    Revenue("12/9/2024", "9.800.000 đ"),
                    Revenue("13/9/2024", "9.800.000 đ"),
                    Revenue("14/9/2024", "9.800.000 đ"),
                    Revenue("15/9/2024", "9.800.000 đ"),
                    Revenue("16/9/2024", "9.800.000 đ")
                )
            )
        }
    }
}