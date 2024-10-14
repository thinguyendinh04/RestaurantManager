package com.dinhthi2004.restaurantmanager.model.user

data class UserResponse(
    val `data`: List<User>,
    val message: Any
)
data class UserResponse1(
    val `data`: User,
    val message: Any
)