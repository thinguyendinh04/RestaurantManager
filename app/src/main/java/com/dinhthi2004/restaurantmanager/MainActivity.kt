package com.dinhthi2004.restaurantmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dinhthi2004.restaurantmanager.presentation.navigation.MyApp
import com.dinhthi2004.restaurantmanager.presentation.screen.waiter.NavigationBarWithScaffold
import com.dinhthi2004.restaurantmanager.ui.theme.RestaurantManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestaurantManagerTheme {
                MyApp()
//                NavigationBarWithScaffold()
            }
        }
    }
}
