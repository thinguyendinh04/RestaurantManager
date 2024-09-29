package com.dinhthi2004.restaurantmanager.presentation.screen.employee.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.employee.EmployeeData

class EmployeeFormViewModel : ViewModel() {

    // Fields for the employee form
    var name = mutableStateOf("")
    var phoneNumber = mutableStateOf("")
    var role = mutableStateOf("Nhân viên") // Default role
    var workShift = mutableStateOf("")

    // Error messages for validation
    var nameError = mutableStateOf<String?>(null)
    var phoneNumberError = mutableStateOf<String?>(null)
    var shiftError = mutableStateOf<String?>(null)

    // Function to create a new EmployeeData object from form input
    fun createEmployee(id: Int): EmployeeData? {
        if (validateFields()) {
            return EmployeeData(
                id = id,
                name = name.value,
                phoneNumber = phoneNumber.value,
                role = role.value,
                shift = workShift.value,
                avatarResId = R.drawable.ic_launcher_background // Default avatar
            )
        }
        return null
    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (name.value.isBlank()) {
            nameError.value = "Name cannot be empty"
            isValid = false
        } else {
            nameError.value = null
        }

        if (phoneNumber.value.isBlank()) {
            phoneNumberError.value = "Phone number cannot be empty"
            isValid = false
        } else {
            phoneNumberError.value = null
        }

        // Validate work shift
        if (workShift.value.isBlank()) {
            shiftError.value = "Shift cannot be empty"
            isValid = false
        } else {
            shiftError.value = null
        }

        return isValid
    }

    fun clearForm() {
        name.value = ""
        phoneNumber.value = ""
        workShift.value = ""
        role.value = "Nhân viên"
    }
}
