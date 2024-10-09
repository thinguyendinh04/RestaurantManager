package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.viewmodel

import androidx.lifecycle.ViewModel
import com.dinhthi2004.restaurantmanager.data.Employee
import com.dinhthi2004.restaurantmanager.data.employ
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.employee.EmployeeData

class HomeEmployeeViewModel : ViewModel() {

    val employees: List<Employee> = employ


    fun filteredEmployees(required: String): List<Employee> {
        return if (required.isEmpty()) {
            employees
        } else {
            employees.filter { it.name.contains(required, ignoreCase = true) }
        }
    }
}
