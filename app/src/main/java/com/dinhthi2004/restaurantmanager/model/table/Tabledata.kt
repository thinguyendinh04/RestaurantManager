package com.dinhthi2004.restaurantmanager.model.table

data class Tabledata (
    val id:Int?=null,
    val table_name:String,
    val status:String,
    val customer_name:String,
    val created_at:String,
    val updated_at:String
)