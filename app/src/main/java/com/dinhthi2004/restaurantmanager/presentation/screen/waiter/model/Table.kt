package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model

data class Table(
    val tableName: String,
    val location: String,
    val capacity: Int,
    val currentGuests: Int = 0,
    val orders: List<OrderItem> = listOf(),
    val totalAmount: Double = 0.0,
    val paymentStatus: String = "",
    val timeIn: String = "",
    val timePaid: String = "",
    var status: String,
    var bookerName: String = "",
    var bookerPhone: String = "",
    var bookingTime: String = "",
    var depositAmount: Double = 0.0
)
