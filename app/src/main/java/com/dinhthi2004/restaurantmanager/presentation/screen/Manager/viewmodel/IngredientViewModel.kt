package com.dinhthi2004.restaurantmanager.presentation.screen.Manager.viewmodel

import androidx.lifecycle.ViewModel
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data.NguyenLieu
import com.dinhthi2004.restaurantmanager.presentation.screen.Manager.data.nguyen

class IngredientViewModel : ViewModel() {
    val ingredients: List<NguyenLieu> = nguyen
}
