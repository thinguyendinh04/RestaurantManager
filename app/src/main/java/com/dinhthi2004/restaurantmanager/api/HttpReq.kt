package com.dinhthi2004.restaurantmanager.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpReq {
    val URL_BASE = "http://10.0.2.2:3000/api/"
    fun getInstance(): ApiService{
        return Retrofit.Builder().baseUrl(URL_BASE).addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
    }
}