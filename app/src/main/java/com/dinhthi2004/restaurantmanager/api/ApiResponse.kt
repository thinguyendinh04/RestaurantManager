package com.dinhthi2004.restaurantmanager.api

import com.dinhthi2004.restaurantmanager.model.Account
import com.dinhthi2004.restaurantmanager.model.Dish

data class AccountRegisterResponse(
    val account: Account,
    val message: Any
)

data class AccountResponse(
    val data: List<Account>,
    val message: Any
)

data class DishResponse(
    val data: List<Dish>,
    val message: Any
)