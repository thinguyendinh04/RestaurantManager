package com.dinhthi2004.restaurantmanager.presentation.screen.waiter.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    title: String,
    showNavigationIcon: Boolean = true,
    onNavigationClick: () -> Unit = {},
    navigationIcon: @Composable (() -> Unit)? = {
        IconButton(onClick = onNavigationClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
    },
    actions: @Composable RowScope.() -> Unit = {}
) {
    val topAppBarPadding = WindowInsets(top = 8)

    (if (showNavigationIcon) navigationIcon else null)?.let {
        CenterAlignedTopAppBar(
            modifier = Modifier.windowInsetsPadding(topAppBarPadding.only(WindowInsetsSides.Top)),
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFFD57D7D),
                titleContentColor = Color.White
            ),
            title = {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = it,
            actions = actions
        )
    }
}
