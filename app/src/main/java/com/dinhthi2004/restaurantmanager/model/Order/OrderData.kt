package com.dinhthi2004.restaurantmanager.model

data class OrderData(
    val id: Int,
    val table_id: Int,
    val dish_id:Int,
    val amount:Int,
    val created_at:String,
    val updated_at:String
)
