package com.dinhthi2004.restaurantmanager.model.dish

data class Dish(
    val id: Int?,
    val id_type: Int,
    val image_url: String?,
    val information: String?,
    val name: String,
    val price: String,
    val status: String,
    val created_at: String?,
    val updated_at: String?
)
