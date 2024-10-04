package com.dinhthi2004.restaurantmanager.domain.repository

import com.dinhthi2004.restaurantmanager.data.remote.dto.MenuDto

interface MenuRepository {
    suspend fun getMenus():List<MenuDto>
}