package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model

data class Order (
    val orderId: String,
    val time: String,
    val recipient: String,
    val address: String,
    val items: List<OrderItem>
)
data class OrderItem(
        val name: String,
        val quantity: Int,
        val price: Double
)
