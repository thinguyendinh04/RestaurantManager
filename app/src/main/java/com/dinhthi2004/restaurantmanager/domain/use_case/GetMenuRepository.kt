package com.dinhthi2004.restaurantmanager.domain.use_case

import com.dinhthi2004.restaurantmanager.common.Resources
import com.dinhthi2004.restaurantmanager.data.remote.dto.MenuDto
import com.dinhthi2004.restaurantmanager.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMenuRepository @Inject constructor(
    private val repository: MenuRepository
) {
    operator fun invoke(): Flow<Resources<List<MenuDto>>> = flow {
        try {
            emit(Resources.Loading())
            val menus = repository.getMenus().map {

            }
            //emit(Resources.Success(menus))
        } catch (e: Exception) {
            emit(Resources.Error("Fail"))
        }
    }
}