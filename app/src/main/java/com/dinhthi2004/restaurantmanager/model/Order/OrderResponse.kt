package com.dinhthi2004.restaurantmanager.model.Order

import com.dinhthi2004.restaurantmanager.model.ingredient.IngredientData

data class OrderResponse(
    val data:OrderData,
    val message:Any
)
