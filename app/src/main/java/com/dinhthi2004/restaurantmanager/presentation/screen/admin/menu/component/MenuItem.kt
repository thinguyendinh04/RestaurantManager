package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component

import com.dinhthi2004.restaurantmanager.R

data class MenuItem(
    val name: String,
    val price: String,
    val category: String,
    val imageResId: Int
)



val sampleItems = listOf(
    MenuItem("Tôm nướng phô mai", "1.200.000đ", "Hải sản", R.drawable.img1),
    MenuItem("Mì Ý sốt kem", "250.000đ", "Best seller", R.drawable.img2),
    MenuItem("Salad cá hồi", "300.000đ", "Khác", R.drawable.img3),
    MenuItem("Tôm nướng phô mai", "1.200.000đ", "Hải sản", R.drawable.img2),
    MenuItem("Tôm nướng phô mai", "1.200.000đ", "Hải sản", R.drawable.img1),
    MenuItem("Mì Ý sốt kem", "250.000đ", "Best seller", R.drawable.img2),
    MenuItem("Salad cá hồi", "300.000đ", "Khác", R.drawable.img3),
    MenuItem("Tôm nướng phô mai", "1.200.000đ", "Hải sản", R.drawable.img2),
    MenuItem("Tôm nướng phô mai", "1.200.000đ", "Hải sản", R.drawable.img1),
    MenuItem("Mì Ý sốt kem", "250.000đ", "Best seller", R.drawable.img2),
    MenuItem("Salad cá hồi", "300.000đ", "Khác", R.drawable.img3),
    MenuItem("Tôm nướng phô mai", "1.200.000đ", "Hải sản", R.drawable.img2)
)
