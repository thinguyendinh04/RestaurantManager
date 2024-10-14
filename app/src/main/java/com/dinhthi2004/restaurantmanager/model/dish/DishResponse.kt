package com.dinhthi2004.restaurantmanager.model.dish

data class DishResponse(
    val `data`: List<Dish>,
    val message: Any
)

data class Dish1Response(
    val `data`: Dish,
    val message: Any
)