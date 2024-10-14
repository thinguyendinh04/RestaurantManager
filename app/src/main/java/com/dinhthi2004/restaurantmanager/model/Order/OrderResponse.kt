package com.dinhthi2004.restaurantmanager.api

import com.dinhthi2004.restaurantmanager.model.OrderData


data class OrderResponse(
    val data: OrderData,
    val message:Any
)
