package com.dinhthi2004.restaurantmanager.model

class Table(
    val id: String, // ID của bàn
    val table_name: String, // Tên bàn
    val table_status: Int, // Trạng thái bàn (0: còn trống, 2313245: đã sử dụng)
    val oder_name: String?, // Tên đơn hàng (có thể là null)
    val id_account: AccountData?
)
