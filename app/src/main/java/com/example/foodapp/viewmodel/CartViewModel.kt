package com.example.foodapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foodapp.model.Cart
import com.example.foodapp.retrofit.RetrofitClient

class CartViewModel(): ViewModel() {
    private var cartList = mutableListOf<Cart>()
    private var res = -1
    private var total = 0
    private var id = 0


    fun setCartList(cartList: MutableList<Cart>) {
        this.cartList = cartList
    }

    fun setTotal(total: Int) {
        this.total = total
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun checkout(){
        RetrofitClient.api.createOrder(id, total, cartList).enqueue(object : retrofit2.Callback<Int> {
            override fun onResponse(
                call: retrofit2.Call<Int>,
                response: retrofit2.Response<Int>
            ) {
                response.body()?.let { received -> res = received }
            }

            override fun onFailure(call: retrofit2.Call<Int>, t: Throwable) {

            }
        })
    }

    fun getResponse(): Int {
        return res
    }
}