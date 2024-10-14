package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data

import com.dinhthi2004.restaurantmanager.R

data class HoaDon(
    val id: Int,
    val banId: String,
    val price: Double,
    val iditems: String,
    val status: Int
) {
    
}

data class Item(val id:String, val name: String, val image: Int, val quantity: Int, val price: Double)

