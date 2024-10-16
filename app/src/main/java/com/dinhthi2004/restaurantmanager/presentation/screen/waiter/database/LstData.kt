package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database

import com.dinhthi2004.restaurantmanager.model.OrderData
import com.dinhthi2004.restaurantmanager.model.bill.BillData
import com.dinhthi2004.restaurantmanager.model.dish.Dish
import com.dinhthi2004.restaurantmanager.model.table.Tabledata

// Dữ liệu mẫu cho Dish
val dishSampleList = listOf(
    Dish(
        id = 1,
        id_type = 1,
        image_url = "https://example.com/image1.jpg",
        information = "Delicious pizza with cheese",
        name = "Pizza Margherita",
        price = "200000",
        status = "Available",
        created_at = "2024-10-01T10:00:00",
        updated_at = "2024-10-10T12:00:00"
    ),
    Dish(
        id = 2,
        id_type = 1,
        image_url = "https://example.com/image2.jpg",
        information = "Spaghetti with meatballs",
        name = "Spaghetti",
        price = "150000",
        status = "Available",
        created_at = "2024-10-02T10:00:00",
        updated_at = "2024-10-10T12:00:00"
    ),
    Dish(
        id = 3,
        id_type = 2,
        image_url = "https://example.com/image3.jpg",
        information = "Grilled chicken with herbs",
        name = "Grilled Chicken",
        price = "250000",
        status = "Available",
        created_at = "2024-10-03T10:00:00",
        updated_at = "2024-10-10T12:00:00"
    ),
    Dish(
        id = 4,
        id_type = 2,
        image_url = "https://example.com/image4.jpg",
        information = "Beef steak with sauce",
        name = "Beef Steak",
        price = "300000",
        status = "Unavailable",
        created_at = "2024-10-04T10:00:00",
        updated_at = "2024-10-10T12:00:00"
    ),
    Dish(
        id = 5,
        id_type = 3,
        image_url = "https://example.com/image5.jpg",
        information = "Fresh salad with vegetables",
        name = "Salad",
        price = "100000",
        status = "Available",
        created_at = "2024-10-05T10:00:00",
        updated_at = "2024-10-10T12:00:00"
    )
)

// Dữ liệu mẫu cho Tabledata
val tableSampleList = listOf(
    Tabledata(
        id = 1,
        table_name = "Table 1",
        status = "Available",
        customer_name = "John Doe",
        created_at = "2024-10-01T08:00:00",
        updated_at = "2024-10-10T12:00:00"
    ),
    Tabledata(
        id = 2,
        table_name = "Table 2",
        status = "Occupied",
        customer_name = "Jane Smith",
        created_at = "2024-10-02T08:00:00",
        updated_at = "2024-10-10T12:00:00"
    ),
    Tabledata(
        id = 3,
        table_name = "Table 3",
        status = "Available",
        customer_name = "",
        created_at = "2024-10-03T08:00:00",
        updated_at = "2024-10-10T12:00:00"
    ),
    Tabledata(
        id = 4,
        table_name = "Table 4",
        status = "Occupied",
        customer_name = "Emily Davis",
        created_at = "2024-10-04T08:00:00",
        updated_at = "2024-10-10T12:00:00"
    ),
    Tabledata(
        id = 5,
        table_name = "Table 5",
        status = "Booked",
        customer_name = "",
        created_at = "2024-10-05T08:00:00",
        updated_at = "2024-10-10T12:00:00"
    )
)

// Dữ liệu mẫu cho OrderData
val orderSampleList = mutableListOf<OrderData>(
    OrderData(
        id = 1,
        table_id = 1,
        dish_id = 1,
        amount = 2,
        created_at = "2024-10-10T12:00:00",
        updated_at = "2024-10-10T12:30:00"
    ),
    OrderData(
        id = 2,
        table_id = 2,
        dish_id = 2,
        amount = 1,
        created_at = "2024-10-10T12:00:00",
        updated_at = "2024-10-10T12:30:00"
    ),
    OrderData(
        id = 3,
        table_id = 3,
        dish_id = 3,
        amount = 3,
        created_at = "2024-10-10T12:00:00",
        updated_at = "2024-10-10T12:30:00"
    ),
    OrderData(
        id = 4,
        table_id = 4,
        dish_id = 4,
        amount = 2,
        created_at = "2024-10-10T12:00:00",
        updated_at = "2024-10-10T12:30:00"
    ),
    OrderData(
        id = 5,
        table_id = 5,
        dish_id = 5,
        amount = 1,
        created_at = "2024-10-10T12:00:00",
        updated_at = "2024-10-10T12:30:00"
    )
)

// Dữ liệu mẫu cho BillData
val billSampleList = listOf(
    BillData(
        id = 1,
        order_id = 1,
        status = 1, // 1: Paid, 0: Unpaid
        tong_tien = "400000",
        created_at = "2024-10-10T13:00:00",
        updated_at = "2024-10-10T13:30:00"
    ),
    BillData(
        id = 2,
        order_id = 2,
        status = 0,
        tong_tien = "150000",
        created_at = "2024-10-10T13:00:00",
        updated_at = "2024-10-10T13:30:00"
    ),
    BillData(
        id = 3,
        order_id = 3,
        status = 1,
        tong_tien = "750000",
        created_at = "2024-10-10T13:00:00",
        updated_at = "2024-10-10T13:30:00"
    ),
    BillData(
        id = 4,
        order_id = 4,
        status = 1,
        tong_tien = "600000",
        created_at = "2024-10-10T13:00:00",
        updated_at = "2024-10-10T13:30:00"
    ),
    BillData(
        id = 5,
        order_id = 5,
        status = 0,
        tong_tien = "100000",
        created_at = "2024-10-10T13:00:00",
        updated_at = "2024-10-10T13:30:00"
    )
)
