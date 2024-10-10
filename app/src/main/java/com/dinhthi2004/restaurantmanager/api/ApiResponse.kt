package com.dinhthi2004.restaurantmanager.api

data class ApiResponse<T>(
    val message: String,
    val data: T
)
data class ApiResponse1<T>(
    val message: String,
    val tables: T
)