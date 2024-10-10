package com.dinhthi2004.restaurantmanager.api

import com.dinhthi2004.restaurantmanager.model.Account
import com.dinhthi2004.restaurantmanager.model.AccountData
import com.dinhthi2004.restaurantmanager.model.Meal

data class ApiResponse<T>(
    val message: String,
    val data: T
)

data class ApiResponse1<T>(
    val message: String,
    val tables: T
)

data class MealResponse(
    val message: String,
    val data: List<Meal>
)

data class AccountResponse(
    val message: String,
    val users: List<Account>
)

data class AccountDetailResponse(
    val message: String,
    val accountDetail: AccountData
)
