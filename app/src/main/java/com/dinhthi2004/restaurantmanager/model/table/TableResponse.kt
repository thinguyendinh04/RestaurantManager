package com.dinhthi2004.restaurantmanager.model.table

import com.dinhthi2004.restaurantmanager.model.dish.Dish

data class TableResponse (
    val `data`: List<Tabledata>,
    val message: Any
)

data class TableResponse1<DataType> (
    val data: DataType,
    val message: Any
)
