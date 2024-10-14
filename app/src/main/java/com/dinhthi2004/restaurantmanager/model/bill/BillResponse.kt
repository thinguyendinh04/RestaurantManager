package com.dinhthi2004.restaurantmanager.model.bill

import com.dinhthi2004.restaurantmanager.model.ingredient.IngredientData

data class BillResponse(
    val data: List<BillData>,
    val message:Any
)
