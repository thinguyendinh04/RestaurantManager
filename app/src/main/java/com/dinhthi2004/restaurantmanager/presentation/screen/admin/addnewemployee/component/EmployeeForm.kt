package com.dinhthi2004.restaurantmanager.presentation.screen.employee.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.addnewemployee.component.RoleDropdown
import com.dinhthi2004.restaurantmanager.presentation.screen.employee.viewmodel.EmployeeFormViewModel

@Composable
fun EmployeeForm(
    viewModel: EmployeeFormViewModel,
    onCancel: () -> Unit ,
    onAdd: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = viewModel.name.value,
            onValueChange = { viewModel.name.value = it },
            label = { Text("Tên:") },
            isError = viewModel.nameError.value != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (viewModel.nameError.value != null) {
            Text(text = viewModel.nameError.value!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.phoneNumber.value,
            onValueChange = { viewModel.phoneNumber.value = it },
            label = { Text("Phone Number:") },
            isError = viewModel.phoneNumberError.value != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (viewModel.phoneNumberError.value != null) {
            Text(text = viewModel.phoneNumberError.value!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        RoleDropdown(viewModel)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.workShift.value,
            onValueChange = { viewModel.workShift.value = it },
            label = { Text("Ca Làm việc:") },
            isError = viewModel.shiftError.value != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (viewModel.shiftError.value != null) {
            Text(text = viewModel.shiftError.value!!, color = MaterialTheme.colorScheme.error)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onCancel, modifier = Modifier.weight(1f)) {
                Text("Hủy")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = onAdd, modifier = Modifier.weight(1f)) {
                Text("Thêm mới")
            }
        }
    }
}
