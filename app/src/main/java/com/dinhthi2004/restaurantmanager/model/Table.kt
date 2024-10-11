package com.dinhthi2004.restaurantmanager.model

class Table(
    val id: String,
    val table_name: String,
    val table_status: Int,
    val oder_name: String?,
    val id_account: AccountData?
)
