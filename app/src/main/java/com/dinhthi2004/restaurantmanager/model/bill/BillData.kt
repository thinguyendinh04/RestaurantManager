package com.dinhthi2004.restaurantmanager.model.bill

data class BillData(
    val id: Int,
    val order_id: Int,
    val status:Int,
    val tong_tien:String,
    val created_at:String,
    val updated_at:String
)
