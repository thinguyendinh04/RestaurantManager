package com.dinhthi2004.restaurantmanager.api

import com.dinhthi2004.restaurantmanager.model.Account
import com.dinhthi2004.restaurantmanager.model.Bill
import com.dinhthi2004.restaurantmanager.model.BillDetail
import com.dinhthi2004.restaurantmanager.model.Ingredient
import com.dinhthi2004.restaurantmanager.model.LoginRequest
import com.dinhthi2004.restaurantmanager.model.Meal
import com.dinhthi2004.restaurantmanager.model.Order.OrderResponse
import com.dinhthi2004.restaurantmanager.model.Table
import com.dinhthi2004.restaurantmanager.model.bill.BillResponse
import com.dinhthi2004.restaurantmanager.model.dish.Dish
import com.dinhthi2004.restaurantmanager.model.dish.Dish1Response
import com.dinhthi2004.restaurantmanager.model.dish.DishResponse
import com.dinhthi2004.restaurantmanager.model.ingredient.IngredientData
import com.dinhthi2004.restaurantmanager.model.ingredient.IngredientResponse
import com.dinhthi2004.restaurantmanager.model.user.User
import com.dinhthi2004.restaurantmanager.model.user.UserResponse
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
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResonse>

    @POST("register")
    suspend fun signup(@Body signupInfo: Account): Response<Account>

    //Admin

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
        @Path("id") id: String,
        @Body dish: Dish
    ): Response<Dish>

    @DELETE("dishes/{id}")
    suspend fun deleteDish(
        @Header("authorization") jwtToken: String,
        @Path("id") id: String
    ): Response<Dish>

    //User
    @GET("accounts")
    suspend fun getAllUser(
        @Header("authorization") jwtToken: String
    ): Response<UserResponse>

    @POST("accounts")
    suspend fun addNewUser(
        @Header("authorization") jwtToken: String,
        @Body user: User
    ): Response<User>

    @POST("accounts/{id}")
    suspend fun updateUser(
        @Header("authorization") jwtToken: String,
        @Path("id") id: String,
        @Body user: User
    ): Response<User>

    @DELETE("accounts/{id}")
    suspend fun deleteUser(
        @Header("authorization") jwtToken: String,
        @Path("id") id: String
    ): Response<User>

    //

    //Manager
    @GET("ingredients")
    suspend fun getAllIngredient(@Header("authorization") jwtToken: String): Response<IngredientResponse>

    @GET("orders/{id}")
    suspend fun get1Order(@Header("authorization") jwtToken: String,@Path("id") id: Int): Response<OrderResponse>

    @GET("dishes/{id}")
    suspend fun get1Dish(@Header("authorization") jwtToken: String,@Path("id") id: Int): Response<Dish1Response>

    @GET("bills")
    suspend fun getAllBill(@Header("authorization") jwtToken: String): Response<BillResponse>

    @POST("ingredients")
    suspend fun add1Ingredient(@Header("authorization") jwtToken: String,@Body ingredient: IngredientData): Response<IngredientData>
    //Waiter

    @GET("ingredient/get-list")
    suspend fun getIngredients(@Header("authorization") jwtToken: String): ApiResponse<List<Ingredient>>

    @GET("tables")
    suspend fun getTables(@Header("authorization") jwtToken: String): ApiResponse1<List<Table>>

    @GET("bill/get-list-bill")
    suspend fun getBills(@Header("authorization") jwtToken: String): ApiResponse<List<Bill>>


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