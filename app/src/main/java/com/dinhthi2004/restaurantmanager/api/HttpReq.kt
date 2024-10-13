package com.dinhthi2004.restaurantmanager.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpReq {
    val URL_BASE = "https://rm-api.imtaedu.com/api/"
    fun getInstance(): ApiService{
        return Retrofit.Builder().baseUrl(URL_BASE).addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
    }
}