package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.database

import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model.Table

// Mock data for "Occupied" tables
val dataTables = listOf(
    Table("Table 1", "Near Window", 4, 3, orderItems, 1200.0, "Unpaid", "12:00", "", "Occupied", "", "", "", 0.0),
    Table("Table 2", "Center Room", 6, 6, orderItems1, 2400.0, "Unpaid", "12:30", "", "Occupied", "", "", "", 0.0),
    Table("Table 3", "Outdoor", 4, 4, orderItems2, 2000.0, "Paid", "10:00", "13:00", "Occupied", "", "", "", 0.0),
    Table("Table 4", "Corner", 2, 2, orderItems3, 500.0, "Unpaid", "11:00", "", "Occupied", "", "", "", 0.0),
    Table("Table 5", "Main Hall", 8, 8, orderItems4, 3200.0, "Paid", "09:30", "12:00", "Occupied", "", "", "", 0.0),
    Table("Table 6", "Terrace", 5, 5, orderItems1, 1500.0, "Unpaid", "12:45", "", "Occupied", "", "", "", 0.0),
    Table("Table 7", "Private Room", 3, 2, orderItems1, 700.0, "Paid", "12:00", "14:30", "Occupied", "", "", "", 0.0),
    Table("Table 8", "By the Bar", 4, 3, orderItems3, 1100.0, "Unpaid", "13:00", "", "Occupied", "", "", "", 0.0),
    Table("Table 9", "Near Bar", 2, 0, listOf(), 0.0, "", "", "", "Empty", "", "", "", 0.0),
    Table("Table 10", "Second Floor", 4, 0, listOf(), 0.0, "", "", "", "Empty", "", "", "", 0.0),
    Table("Table 11", "Garden", 6, 0, listOf(), 0.0, "", "", "", "Empty", "", "", "", 0.0),
    Table("Table 12", "Front Entrance", 3, 0, listOf(), 0.0, "", "", "", "Empty", "", "", "", 0.0),
    Table("Table 13", "Balcony", 2, 0, listOf(), 0.0, "", "", "", "Empty", "", "", "", 0.0),
    Table("Table 14", "Near Kitchen", 5, 0, listOf(), 0.0, "", "", "", "Empty", "", "", "", 0.0),
    Table("Table 15", "VIP Section", 8, 0, listOf(), 0.0, "", "", "", "Booked", "John Doe", "1234567890", "15:00", 500.0),
    Table("Table 16", "Near Entrance", 2, 0, listOf(), 0.0, "", "", "", "Booked", "Jane Smith", "0987654321", "16:00", 200.0),
    Table("Table 17", "Center Room", 6, 0, listOf(), 0.0, "", "", "", "Booked", "Alice Johnson", "9876543210", "18:00", 300.0),
    Table("Table 18", "Garden Area", 4, 0, listOf(), 0.0, "", "", "", "Booked", "Bob Brown", "2345678901", "19:00", 250.0),
    Table("Table 19", "Window Side", 3, 0, listOf(), 0.0, "", "", "", "Booked", "Carol White", "3456789012", "20:00", 150.0),
    Table("Table 20", "Rooftop", 2, 0, listOf(), 0.0, "", "", "", "Booked", "Dave Black", "4567890123", "17:30", 400.0)
)
