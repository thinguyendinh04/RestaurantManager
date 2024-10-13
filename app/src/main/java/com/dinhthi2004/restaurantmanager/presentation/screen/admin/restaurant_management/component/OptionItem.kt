package com.dinhthi2004.restaurantmanager.presentation.screen.admin.restaurant_management.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OptionItem(
    iconRes: Int,
    title: String,
    onClick: () -> Unit,
    backgroundColor: Color = Color.LightGray,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp), // Bo góc tùy chỉnh
    padding: Dp = 8.dp // Padding tùy chỉnh
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = padding)
            .clip(shape)
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(vertical = padding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = "Arrow Icon",
            modifier = Modifier
                .size(24.dp)
                .padding(end = 16.dp)
        )
    }
}

