package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database

import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.Order
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.OrderItem

val waitingOrders = listOf(
    Order(
        orderId = "0001",
        time = "09:30",
        recipient = "Nguyễn Văn Tý",
        address = "13 đường P. Trịnh Văn Bô, Xuân Phương, Nam Từ Liêm, Hà Nội, Vietnam",
        items = listOf(
            OrderItem(name = "Cơm thập cẩm", quantity = 1, price = 100000.0),
            OrderItem(name = "Thịt nướng giòn", quantity = 1, price = 60000.0)
        )
    ),
    Order(
        orderId = "0002",
        time = "10:00",
        recipient = "Trần Thị B",
        address = "456 Đường XYZ, Hà Nội, Vietnam",
        items = listOf(
            OrderItem(name = "Bánh Mì", quantity = 2, price = 20000.0),
            OrderItem(name = "Nước Ngọt", quantity = 1, price = 15000.0)
        )
    )
    // Bạn có thể thêm nhiều đơn hàng ở đây
)
