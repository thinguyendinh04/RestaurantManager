package com.dinhthi2004.restaurantmanager.model

//data class Table (
//    var table_name: String,
//    var table_status: Int, // 0: con trong, 1: dang su dung
//    var oder_name: String?,
//)

data class TableResponse(
    val message: String,
    val tables: List<Table>
)

data class Table(
    val table_name: String,
    val table_status: Int,
    val oder_name: String? // Dùng ? nếu không chắc chắn nó sẽ luôn có giá trị
)