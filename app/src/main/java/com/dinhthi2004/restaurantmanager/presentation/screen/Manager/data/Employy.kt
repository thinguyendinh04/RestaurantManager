package com.dinhthi2004.restaurantmanager.data

import com.dinhthi2004.restaurantmanager.R

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

// Danh sách nhân viên mẫu
val employ = listOf(
    Employee(1, "Nguyen Van C", R.drawable.employee_logo, "0975432178", 2, "Nhân Viên", 1204.5, 23.5),
    Employee(2, "Nguyen Van D", R.drawable.employee_logo, "0975432179", 1, "Nhân Viên", 1114.2, 222.3),
    Employee(3, "Nguyen Van F", R.drawable.employee_logo, "0975432170", 0, "Nhân Viên", 3322.4, 444.3),
    Employee(4, "Nguyen Van V", R.drawable.employee_logo, "0975432176", 2, "Nhân Viên", 3344.4, 444.5),
    Employee(5, "Nguyen Van T", R.drawable.employee_logo, "0975432174", 1, "Nhân Viên", 999.777, 444.7)
)
