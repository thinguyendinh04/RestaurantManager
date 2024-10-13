package com.dinhthi2004.restaurantmanager.model

data class Account(
    val _id: String,
    val username: String,
    val role: Int,
    val sdt: String,
    val full_name: String,
    val image_url: String,
    var token: String? = null
)