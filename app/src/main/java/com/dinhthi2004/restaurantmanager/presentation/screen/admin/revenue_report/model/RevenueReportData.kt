package com.dinhthi2004.restaurantmanager.presentation.screen.admin.revenue_report.model

data class RevenueReportData(
    val startDate: String,
    val endDate: String,
    val totalRevenue: String,
    val revenues: List<Revenue>
)

data class Revenue(
    val date: String,
    val revenue: String
)