package com.dinhthi2004.restaurantmanager.presentation.screen.admin.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dinhthi2004.restaurantmanager.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldSearch() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        trailingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 10.dp)
            )
        },
        label = { Text(text = "Searching for ..... ") },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Gray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, end = 24.dp, start = 24.dp)
            .shadow(3.dp, shape = RoundedCornerShape(10.dp))
            .background(Color.White, CircleShape)
    )
}