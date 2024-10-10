package com.dinhthi2004.restaurantmanager.api

import com.dinhthi2004.restaurantmanager.model.Account
import com.dinhthi2004.restaurantmanager.model.Table
import com.dinhthi2004.restaurantmanager.model.TableResponse
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
    suspend fun signup(@Body signupInfo: Any): Response<Any>

    @GET
    suspend fun <Model> getFromApi(@Header("authorization") jwtToken: String, @Url endpoint: String, @Path("id") id: String): Response<Model> // chỉ cần chuyền id khi muốn lấy 1 món ăn(meal) theo id không thì chuyền 1 String trống ""

    @POST
    suspend fun <Model> addToApi(@Header("authorization") jwtToken: String, @Url endpoint: String, @Body inputModel: Model): Response<Model>

    @PUT
    suspend fun <Model> updateToApi(@Header("authorization") jwtToken: String, @Url endpoint: String, @Path("id") id: String, @Body inputModel: Model): Response<Model>

    @DELETE
    suspend fun <Model> deleteFromApi(@Header("authorization") jwtToken: String, @Url endpoint: String, @Path("id") id:String): Response<Model>

    @GET("tables")
    suspend fun getTables(@Header("authorization") token: String): Response<TableResponse>
}