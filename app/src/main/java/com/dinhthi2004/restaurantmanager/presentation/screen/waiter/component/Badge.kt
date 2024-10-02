package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgeM3(
    badgeCount: String = "99+",
    icon: ImageVector = Icons.Default.Favorite,
    iconSize: Int = 40 // Kích thước mặc định cho icon
) {
    BadgedBox(
        badge = {
            Badge { Text(text = badgeCount) }
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Favorite",
            modifier = Modifier.size(iconSize.dp)
        )
    }
}
