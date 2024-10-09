package com.dinhthi2004.restaurantmanager.presentation.screen.Manager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.AddIngredientDialog
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.components.NguyenLieuItem
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data.nguyen

@Composable
fun HomeIngredients(navigationController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }


        if (showDialog) {
            AddIngredientDialog(onDismiss = { showDialog = false })
        }

        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
            Column {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxHeight(),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        nguyen.size
                    ) { index ->
                        NguyenLieuItem(index){

                        }
                    }
                }
            }

            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 17.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Icon",
                    modifier = Modifier.size(45.dp),
                    tint = Color.Black
                )
            }
        }

}
