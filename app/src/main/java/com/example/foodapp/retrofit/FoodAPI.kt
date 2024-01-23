package com.example.foodapp.retrofit

import com.example.foodapp.model.Cart
import com.example.foodapp.model.Details
import com.example.foodapp.model.Food
import com.example.foodapp.model.Order
import com.example.foodapp.model.Orders
import com.example.foodapp.model.User
import com.example.foodapp.model.foods
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodAPI {
    @GET("/food")
    fun getFoods(): Call<foods>


    @GET("/food/{id}")
    fun getFoodById(@Path("id") id: Int): Call<Food>

    @POST("/user/signup")
    fun registerUser(
        @Query("user_name") name: String,
        @Query("user_email") email: String,
        @Query("user_password") password: String,
        @Query("user_address") address: String,
        @Query("user_phone") phone: String
    ): Call<Int>

    @POST("/user/login")
    fun checkUser(
        @Query("user_email") email: String,
        @Query("user_password") password: String
    ): Call<Int>

    @GET("/user/{id}")
    fun getUser(@Path("id") id: Int): Call<User>

    @GET("/name/{id}")
    fun getNameById(@Path("id") id: Int): Call<String>

    @POST("/order/create")
    fun createOrder(
        @Query("user_id") user_id: Int,
        @Query("order_total") food_id: Int,
        @Body cart: List<Cart>
    ): Call<Int>

    @GET("/order/{id}/{status}")
    fun getOrders(
        @Path("id") id: Int,
        @Path("status") status: Int
    ): Call<Orders>

    @GET("/detail/{order_id}")
    fun getDetails(
        @Path("order_id") id: Int,
    ): Call<Details>

}