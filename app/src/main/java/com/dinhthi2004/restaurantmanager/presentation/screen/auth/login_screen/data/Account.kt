package com.dinhthi2004.restaurantmanager.presentation.screen.auth.login_screen.data

data class Account(
    val email: String,
    val password: String,
    val role: String
)

fun getSampleAccounts(): List<Account> {
    return listOf(
        Account("admin@example.com", "admin123", "Admin"),
        Account("manager@example.com", "manager123", "Manager"),
        Account("waiter@example.com", "waiter123", "Waiter"),
        Account("user@example.com", "user123", "Waiter")
    )
}
