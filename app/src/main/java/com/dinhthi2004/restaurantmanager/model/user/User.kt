package com.dinhthi2004.restaurantmanager.model.user

data class User(
    val created_at: String,
    val email: String,
    val full_name: String,
    val id: Int,
    val image_url: String?,
    val role: Int,
    val sdt: String,
    val updated_at: String,
    val token: String?
)