package com.dinhthi2004.restaurantmanager.model

data class BillResponse1<DataType>(
    val message: String?,
    val data: DataType
)