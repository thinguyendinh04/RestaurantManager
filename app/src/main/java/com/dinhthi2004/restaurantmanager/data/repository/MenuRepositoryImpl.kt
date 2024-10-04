package com.dinhthi2004.restaurantmanager.data.repository

import com.dinhthi2004.restaurantmanager.data.remote.ApiService
import com.dinhthi2004.restaurantmanager.data.remote.dto.MenuDto
import com.dinhthi2004.restaurantmanager.domain.repository.MenuRepository
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MenuRepository {
    override suspend fun getMenus(): List<MenuDto> {
        return apiService.getListMenu()
    }
}