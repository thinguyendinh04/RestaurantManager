package com.dinhthi2004.restaurantmanager.presentation.screen.admin.addnewemployee.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.dinhthi2004.restaurantmanager.presentation.screen.employee.viewmodel.EmployeeFormViewModel

@Composable
fun RoleDropdown(viewModel: EmployeeFormViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val roles = listOf("Manager", "Waiter")
    val selectedRole = viewModel.role.value

    // Display the OutlinedTextField as a dropdown
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedRole,
            onValueChange = {},
            label = { Text("Chức vụ") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            roles.forEach { role ->
                DropdownMenuItem(
                    text = { Text(role) },
                    onClick = {
                        viewModel.role.value = role  // Update the selected role in the ViewModel
                        expanded = false
                    }
                )
            }
        }
    }
}