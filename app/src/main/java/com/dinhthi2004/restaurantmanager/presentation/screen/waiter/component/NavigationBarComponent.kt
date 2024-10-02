package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun NavigationBarComponent(
    barItems: List<BarItem>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar {
        barItems.forEachIndexed { index, barItem ->
            val selected = selectedItem == index
            NavigationBarItem(
                selected = selected,
                onClick = { onItemSelected(index) },
                icon = {
                    if (barItem.badgeCount != null) {
                        BadgedBox(
                            badge = { Badge { Text(text = barItem.badgeCount) } }
                        ) {
                            Icon(
                                imageVector = if (selected) barItem.selectedIcon else barItem.unselectedIcon,
                                contentDescription = barItem.title,
                                tint = if (selected) Color(0xfffe763e) else Color.Unspecified
                            )
                        }
                    } else {
                        Icon(
                            imageVector = if (selected) barItem.selectedIcon else barItem.unselectedIcon,
                            contentDescription = barItem.title,
                            tint = if (selected) Color(0xfffe763e) else Color.Unspecified
                        )
                    }
                },
                label = {
                    Text(
                        text = barItem.title,
                        color = if (selected) Color(0xfffe763e) else Color.Unspecified
                    )
                },
                alwaysShowLabel = selected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xfffe763e),
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color(0xfffe763e),
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}

data class BarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
    val badgeCount: String? = null
)
