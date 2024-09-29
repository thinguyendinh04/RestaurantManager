package com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component.MenuItem
import com.dinhthi2004.restaurantmanager.presentation.screen.admin.menu.component.sampleItems

class MenuManagementViewModel : ViewModel() {

    // The entire list of menu items
    private val _menuItems = mutableStateListOf<MenuItem>().apply { addAll(sampleItems) }
    val menuItems: List<MenuItem> = _menuItems

    // State for search query and selected category
    var searchQuery = mutableStateOf("")
    var selectedCategory = mutableStateOf("Tất cả")

    // Filter the items based on search query and selected category
    val filteredItems: List<MenuItem>
        get() {
            return _menuItems.filter { item ->
                (selectedCategory.value == "Tất cả" || item.category == selectedCategory.value) &&
                        (searchQuery.value.isEmpty() || item.name.contains(searchQuery.value, ignoreCase = true))
            }
        }

    // Delete an item from the list
    fun deleteMenuItem(item: MenuItem) {
        _menuItems.remove(item)
    }

    // Update the search query
    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    // Update the selected category
    fun updateSelectedCategory(category: String) {
        selectedCategory.value = category
    }
}