package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model

import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database.OrderItem

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
    val status: String,
    val bookerName: String = "",
    val bookerPhone: String = "",
    val bookingTime: String = "",
    val depositAmount: Double = 0.0
)
