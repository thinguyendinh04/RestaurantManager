package com.dinhthi2004.restaurantmanager.model

data class Dish(
    val created_at: String,
    val id: Int,
    val id_type: Int,
    val image_url: String,
    val information: String,
    val name: String,
    val price: String,
    val status: String,
    val updated_at: String
)