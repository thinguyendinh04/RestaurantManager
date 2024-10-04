package com.dinhthi2004.restaurantmanager.data.remote

import com.dinhthi2004.restaurantmanager.data.remote.dto.MenuDto
import retrofit2.http.GET

interface ApiService {
    @GET("/endpoint")
    suspend fun getListMenu():List<MenuDto>
}