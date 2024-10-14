package com.dinhthi2004.restaurantmanager.model

data class BillResponse <DataType> (
    val message: String?,
    val data: DataType
)