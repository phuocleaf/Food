package com.example.foodapp.model

data class Cart (
    val food_id: Int,
    val food_name: String,
    val food_price: Int,
    val food_image: String,
    var quantity: Int
)

