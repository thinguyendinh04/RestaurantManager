package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data

data class Table(
    val id: Int,
    val ban: String,
    val name: String,
    val phone: String,
    val quantity: Number,
    val description: String,
    val isOnline: Boolean
)

val items = listOf(
    Table(1, "12", "Nguyen B", "0975432178", 4, "không có", isOnline = false),
    Table(2, "13", "Nguyen C", "0975432179", 3, "Quán này ok", isOnline = true),
    Table(3, "15", "Nguyen V", "0975432174", 4, "", isOnline = true),
    Table(4, "14", "Nguyen D", "0975432175", 12, "tôi sẽ liên hệ với bạn sau", isOnline = false),
    Table(5, "11", "Nguyen T", "0975432176", 5, "giúp tôi tổ chức tiệc", isOnline = true)
)
