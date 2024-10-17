package com.dinhthi2004.restaurantmanager.model.table

data class Tabledata (
    val id:Int?=null,
    val table_name:String,
    var status:String,
    var customer_name:String,
    val created_at:String,
    val updated_at:String
)