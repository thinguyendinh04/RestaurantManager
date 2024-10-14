package com.dinhthi2004.restaurantmanager.model.user

data class UserRegistration(
    val full_name: String,
    val email: String,
    val password: String,
    val role: Int
)
