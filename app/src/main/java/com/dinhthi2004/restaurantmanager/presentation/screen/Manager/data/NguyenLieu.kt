package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data

import com.dinhthi2004.restaurantmanager.R

data class NguyenLieu(
    val id: Int,
    val name: String,
    val image: Int,
    val ton: Int,
    val nhap: Int,
    val price: Double
)
val nguyen = listOf(
    NguyenLieu(1, "Súp Nơ",  R.drawable.sup_no, 2, 3, 100.0),
    NguyenLieu(2, "Cua Cà Mau", R.drawable.cua, 2, 3, 100.0),
    NguyenLieu(3, "Tôm Hùm", R.drawable.alaska, 2, 3, 100.0),
    NguyenLieu(4, "Thịt Bò",  R.drawable.thit_bo, 2, 3, 100.0),
    NguyenLieu(6, "Thịt Lợn", R.drawable.thit_lon, 2, 3, 100.0),
    NguyenLieu(9, "Súp Nơ",  R.drawable.sup_no, 2, 3, 100.0),
    NguyenLieu(10, "Súp Nơ", R.drawable.sup_no, 2, 3, 100.0),
    NguyenLieu(5, "Thịt Lợn", R.drawable.thit_lon, 2, 3, 100.0),
    NguyenLieu(7, "Thịt Lợn", R.drawable.thit_lon, 2, 3, 100.0),
    NguyenLieu(8, "Giấy Ăn",  R.drawable.paper, 2, 3, 100.0),
    NguyenLieu(12, "Giấy Ăn",  R.drawable.paper, 2, 3, 100.0),
)
