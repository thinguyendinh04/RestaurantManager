package com.dinhthi2004.restaurantmanager.model

data class Meal(
    val _id: String,
    val name: String,
    val price: Int,
    val status: Int,
    val info: String,
    val rating: Float,
    val type_id: String,

)