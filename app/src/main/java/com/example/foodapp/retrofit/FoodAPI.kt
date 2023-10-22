package com.example.foodapp.retrofit

import com.example.foodapp.model.Food
import com.example.foodapp.model.foods
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodAPI {
    @GET("/food")
    fun getFoods(): Call<foods>

    @GET("/food/{id}")
    fun getFoodById(@Path("id") id: Int): Call<Food>
}