package com.dinhthi2004.restaurantmanager.api

import com.dinhthi2004.restaurantmanager.model.Account
import com.dinhthi2004.restaurantmanager.model.Bill
import com.dinhthi2004.restaurantmanager.model.BillDetail
import com.dinhthi2004.restaurantmanager.model.Ingredient
import com.dinhthi2004.restaurantmanager.model.Meal
import com.dinhthi2004.restaurantmanager.model.Table
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {

    @POST("login")
    suspend fun login(@Body loginInfo: Account): Response<Account>

    @POST("register")
    suspend fun signup(@Body signupInfo: Account): Response<Account>

    @GET("ingredient/get-list")
    suspend fun getIngredients(@Header("authorization") jwtToken: String): ApiResponse<List<Ingredient>>

    @GET("tables")
    suspend fun getTables(@Header("authorization") jwtToken: String): ApiResponse1<List<Table>>

    @GET("bill/get-list-bill")
    suspend fun getBills(@Header("authorization") jwtToken: String): ApiResponse<List<Bill>>

    @GET("billDetail/get-list-billDetail")
    suspend fun getBillDetails(@Header("authorization") jwtToken: String): ApiResponse<List<BillDetail>>

    @GET("meal/get-meals")
    suspend fun getMeals(
        @Header("authorization") jwtToken: String,
    ): Response<MealResponse>

    @POST("")
    suspend fun addMeal(
        @Header("authorization") jwtToken: String,
        @Body meal: Meal
    ): Response<MealResponse>

    @DELETE("meal/delete-meal/:mealID")
    suspend fun deleteMeal(
        @Header("authorization") jwtToken: String,
        @Path("mealId") mealId:String
    ): Response<MealResponse>

    @GET("users")
    suspend fun getUser(
        @Header("authorization") jwtToken: String,
    ): Response<AccountResponse>

    @GET("user")
    suspend fun getUserInformation(
        @Header("authorization") jwtToken: String,
    ): Response<AccountDetailResponse>

    @DELETE
    suspend fun deleteUser(
        @Header("authorization") jwtToken: String,
    ): Response<Account>
}