package com.dinhthi2004.restaurantmanager.model.ingredient

import com.dinhthi2004.restaurantmanager.model.Ingredient

data class IngredientResponse(
    val `data`:List<IngredientData>,
    val message:Any
)
