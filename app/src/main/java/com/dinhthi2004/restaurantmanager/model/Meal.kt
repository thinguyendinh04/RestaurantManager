package com.dinhthi2004.restaurantmanager.model

import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    val _id: String,
    val name: String,
    val price: Int,
    val status: Int,
    val type_id: String,
    val info: String,
    val rating: Double,
)