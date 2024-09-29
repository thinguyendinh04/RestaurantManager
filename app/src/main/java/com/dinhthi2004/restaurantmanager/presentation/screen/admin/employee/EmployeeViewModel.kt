package com.dinhthi2004.restaurantmanager.presentation.screen.admin.employee

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.dinhthi2004.restaurantmanager.R

class EmployeeViewModel : ViewModel() {

    private val _employees = mutableStateListOf(
        EmployeeData(
            1,
            "Nguyễn Đình Thi",
            "09xxxxxxxx",
            "11h-21h",
            "Nhân viên",
            R.drawable.ic_launcher_background
        ),
        EmployeeData(
            2,
            "Lê Văn Minh",
            "09xxxxxxxx",
            "10h-19h",
            "Quản lý",
            R.drawable.ic_launcher_foreground
        ),
        EmployeeData(
            3,
            "Trần Quang Khải",
            "09xxxxxxxx",
            "08h-17h",
            "Nhân viên",
            R.drawable.ic_launcher_foreground
        )
    )

    val employees: List<EmployeeData> = _employees

    fun filteredEmployees(query: String): List<EmployeeData> {
        return if (query.isEmpty()) {
            employees
        } else {
            employees.filter { it.name.contains(query, ignoreCase = true) }
        }
    }


    fun deleteEmployee(id: Int) {
        _employees.removeIf { it.id == id }
    }
}

data class EmployeeData(
    val id: Int,
    val name: String,
    val phoneNumber: String,
    val shift: String,
    val role: String,
    val avatarResId: Int
)
