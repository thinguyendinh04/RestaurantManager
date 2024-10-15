package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model

import com.dinhthi2004.restaurantmanager.R

data class dataProduct(
    val name: String,
    val price: String,
    val category: String,
    val imageResId: Int
)


val ListData = listOf(
    dataProduct("Tôm nướng phô mai", "1.200.000đ", "Hải sản", R.drawable.img1),
    dataProduct("Mì Ý sốt kem", "250.000đ", "Best seller", R.drawable.img2),
    dataProduct("Salad cá hồi", "300.000đ", "Khác", R.drawable.img3),
    dataProduct("Tôm nướng phô mai", "1.200.000đ", "Hải sản", R.drawable.img2),
    dataProduct("Tôm nướng phô mai", "1.200.000đ", "Hải sản", R.drawable.img1),
    dataProduct("Mì Ý sốt kem", "250.000đ", "Best seller", R.drawable.img2),
    dataProduct("Salad cá hồi", "300.000đ", "Khác", R.drawable.img3),
    dataProduct("Tôm nướng phô mai", "1.200.000đ", "Hải sản", R.drawable.img2),
    dataProduct("Tôm nướng phô mai", "1.200.000đ", "Hải sản", R.drawable.img1),
    dataProduct("Mì Ý sốt kem", "250.000đ", "Best seller", R.drawable.img2),
    dataProduct("Salad cá hồi", "300.000đ", "Khác", R.drawable.img3),
    dataProduct("Tôm nướng phô mai", "1.200.000đ", "Hải sản", R.drawable.img2)
)
