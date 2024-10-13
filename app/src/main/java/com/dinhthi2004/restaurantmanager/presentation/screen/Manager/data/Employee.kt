package com.dinhthi2004.restaurantmanager.data

import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.navigation.bottomnav.BottomNavItem

data class Employee(
    val id: Int,
    val name: String,
    val image: Int,
    val phone: String,
    val status: Int,
    val job: String,
    val luongCb: Double,
    val thuong: Double
)

