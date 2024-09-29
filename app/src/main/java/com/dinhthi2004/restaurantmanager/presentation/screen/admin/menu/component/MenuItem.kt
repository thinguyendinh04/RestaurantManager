package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component

import com.dinhthi2004.restaurantmanager.R

data class MenuItem(
    val name: String,
    val price: String,
    val category: String,
    val imageResId: Int
)



val sampleItems = listOf(
    MenuItem("Tôm hùm bỏ lò", "1.500.000đ", "Hải sản", R.drawable.img1),
    MenuItem("Mì Ý sốt cà chua", "200.000đ", "Best seller", R.drawable.img2),
    MenuItem("Salad ức gà", "280.000đ", "Salad", R.drawable.img3),
    MenuItem("Bò nướng tiêu đen", "800.000đ", "Món chính", R.drawable.thit_bo),
    MenuItem("Cơm chiên hải sản", "350.000đ", "Hải sản", R.drawable.alaska),
    MenuItem("Súp tôm hùm", "600.000đ", "Khai vị", R.drawable.cua),
    MenuItem("Sườn nướng mật ong", "750.000đ", "Món chính", R.drawable.thit_bo),
    MenuItem("Cá hồi sốt bơ tỏi", "900.000đ", "Hải sản", R.drawable.img1),
    MenuItem("Lẩu Thái hải sản", "1.200.000đ", "Best seller", R.drawable.img3),
    MenuItem("Gà chiên giòn", "320.000đ", "Món chính", R.drawable.img3)

)
