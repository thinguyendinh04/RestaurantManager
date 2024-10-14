package com.dinhthi2004.restaurantmanager.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dinhthi2004.restaurantmanager.R
import com.dinhthi2004.restaurantmanager.model.Account
import com.dinhthi2004.restaurantmanager.model.user.User

@Composable
fun EmployeeCard(
    user: User,
    onDeleteClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onClick()
            },
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Họ Tên: ${user.full_name}",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Tài khoản: ${user.email}",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Chức vụ: ${getRoleText(user.role)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

fun getRoleText(role: Int): String {
    return when (role) {
        1 -> "Admin"
        2 -> "Manager"
        3 -> "Waiter"
        else -> "Unknown"
    }
}
