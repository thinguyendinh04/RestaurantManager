package com.dinhthi2004.restaurantmanager.api

import com.dinhthi2004.restaurantmanager.model.Account
import com.dinhthi2004.restaurantmanager.model.Bill
import com.dinhthi2004.restaurantmanager.model.BillDetail
import com.dinhthi2004.restaurantmanager.model.Dish
import com.dinhthi2004.restaurantmanager.model.Ingredient
import com.dinhthi2004.restaurantmanager.model.Table
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    //Auth

    //Admin
    //User
    @POST("register")
    suspend fun register(
        @Header("authorization") jwtToken: String,
        @Body account: Account
    )

    @GET("accounts")
    suspend fun getAllAccount(
        @Header("authorization") jwtToken: String
    ): Response<AccountResponse>

    @POST("accounts")
    suspend fun addNewAccount(
        @Header("authorization") jwtToken: String,
        @Body account: Account
    ): Response<Account>

    @PUT("accounts/{id}")
    suspend fun updateAccount(
        @Header("authorization") jwtToken: String,
        @Body account: Account
    ): Response<Account>

    @PUT("accounts/{id}")
    suspend fun updateRoleAccount(
        @Header("authorization") jwtToken: String,
        @Body account: Account
    ): Response<Account>

    @DELETE("accounts/{id}")
    suspend fun deleteAccount(
        @Header("authorization") jwtToken: String,
    ): Response<Account>

    //DishType


    //Dish
    @GET("dishes")
    suspend fun getAllDish(
        @Header("authorization") jwtToken: String
    ): Response<DishResponse>

    @POST("dishes")
    suspend fun addNewDish(
        @Header("authorization") jwtToken: String,
        @Body dish: Dish
    ): Response<Dish>

    @POST("dishes/{id}")
    suspend fun updateDish(
        @Header("authorization") jwtToken: String,
        @Body dish: Dish
    ): Response<Dish>

    @DELETE("dishes/{id}")
    suspend fun deleteDish(
        @Header("authorization") jwtToken: String
    ): Response<Dish>



    //Manager

    //Waiter

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
    @POST("meal/add-meal")
    suspend fun addMeal(
        @Header("authorization") jwtToken: String,
        @Body meal: Meal
    ): Response<Meal>
    @DELETE("meal/delete-meal")
    suspend fun deleteMeal(
        @Header("authorization") jwtToken: String,
        @Path("mealId") mealId: String
    ): Response<MealResponse>
    @GET("users")
    suspend fun getUser(
        @Header("authorization") jwtToken: String,
    ): Response<AccountResponse>
    @GET("user")
    suspend fun getUserInformation(
        @Header("authorization") jwtToken: String,
        @Path("userId") idAccount: String
    ): Response<AccountDetailResponse>
    @GET
    suspend fun get1Meal(
        @Header("authorization") jwtToken: String,
        @Url endpoint: String
    ): Response<Meal>
    @GET
    suspend fun searchMeals(
        @Header("authorization") jwtToken: String,
        @Url endpoint: String,
        @Query("mealname") mealname: String
    ): Response<ArrayList<Meal>>
    @POST("table")
    suspend fun addTable(
        @Header("authorization") jwtToken: String,
        @Body table: Table
    ): ApiResponse2<Table>
    @POST("ingredient/add")
    suspend fun addIngredient(
        @Header("authorization") jwtToken: String,
        @Body ingredient: Ingredient
    ): ApiResponse<Ingredient>
    @PUT
    suspend fun update1Meal(
        @Header("authorization") jwtToken: String,
        @Url endpoint: String,
        @Path("id") id: String,
        @Body inputModel: Meal
    ): Response<Meal>
    @DELETE
    suspend fun delete1Meal(
        @Header("authorization") jwtToken: String,
        @Url endpoint: String,
        @Path("id") id: String
    ): Response<Meal>
    @GET("meal-type/get-meal-types")
    suspend fun getMealType(
        @Header("authorization") jwtToken: String,
    ): Response<MealTypeResponse>
}