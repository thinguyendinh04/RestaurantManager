package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data

import com.dinhthi2004.restaurantmanager.R

data class HoaDon(
    val id: Int,
    val banId: String,
    val price: Double,
    val items: List<Item>,
    val status: Int
) {
    fun calculateTotalPrice(): Double {
        return items.sumOf { it.price }
    }
}

data class Item(val name: String, val image: Int, val quantity: Int, val price: Double)
val bill = listOf(
    HoaDon(
        1, "Bàn 11", 256.000, items = listOf(
            Item(name = "Salat Cá Ngừ", R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Tôm Sú", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Cua Alaska", R.drawable.cua, quantity = 1, price = 10000.0)
        ), 1
    ),
    HoaDon(
        2, "Bàn 13", 256.000, items = listOf(
            Item(name = "Salat Cá Ngừ", R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Salat Cá Ngừ1", R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Salat Cá Ngừ2", R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Tôm Sú", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Cua Alaska", R.drawable.cua, quantity = 1, price = 10000.0)
        ), 0
    ),
    HoaDon(
        3, "Bàn 14", 256.000, items = listOf(
            Item(name = "Salat Cá Ngừ", R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Tôm Sú", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú1", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú2", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Cua Alaska", R.drawable.cua, quantity = 1, price = 10000.0)
        ), 1
    ),
    HoaDon(
        4, "Bàn 19", 256.000, items = listOf(
            Item(name = "Salat Cá Ngừ", R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Tôm Sú", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Cua Alaska", R.drawable.cua, quantity = 1, price = 10000.0),
            Item(name = "Cua Alaska1", R.drawable.cua, quantity = 3, price = 700.0),
            Item(name = "Cua Alaska3", R.drawable.cua, quantity = 3, price = 700.0)
        ), 0
    ),
    HoaDon(
        5, "Bàn 9", 256.000, items = listOf(
            Item(name = "Salat Cá Ngừ", R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Tôm Sú", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú4", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Cua Alaska", R.drawable.cua, quantity = 1, price = 10000.0)
        ), 1
    ),
    HoaDon(
        6, "Bàn 34", 256.000, items = listOf(
            Item(name = "Salat Cá Ngừ", R.drawable.cua, quantity = 2, price = 20000.0),
            Item(name = "Tôm Sú", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú4", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Tôm Sú5", R.drawable.cua, quantity = 2, price = 10000.0),
            Item(name = "Cua Alaska", R.drawable.cua, quantity = 1, price = 10000.0)
        ), 1
    )
)
