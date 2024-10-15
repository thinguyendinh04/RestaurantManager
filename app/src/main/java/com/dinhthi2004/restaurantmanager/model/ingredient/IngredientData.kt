package com.dinhthi2004.restaurantmanager.model.ingredient

data class IngredientData(
    val id: Int?=null,
    val name: String,
    val image:String?,
    val amount:Int,
    val created_at:String,
    val updated_at:String
)
