package com.dinhthi2004.restaurantmanager.model

data class Account(
    val _id: String,
    val username: String,
    val password: String,
    val role: Int,
    var token: String? = null
)