package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.model.Cart
import com.example.foodapp.model.Food
import com.example.foodapp.model.Order
import com.example.foodapp.model.Orders
import com.example.foodapp.model.foods
import com.example.foodapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.Objects

class OrdersViewModel(): ViewModel() {

    private var id = 0
    private var status = 0

    private var ordersLiveData = MutableLiveData<List<Order>>()

    fun setOrders(id: Int, status: Int){
        this.id = id
        this.status = status
    }

    fun getCategories(){
        RetrofitClient.api.getOrders(id,status).enqueue(object : Callback<Orders>{
            override fun onResponse(call: Call<Orders>, response: Response<Orders>) {
                response.body()?.let {categories1 ->
                    ordersLiveData.postValue(categories1.orders)
                }
            }

            override fun onFailure(call: Call<Orders>, t: Throwable) {
                Log.e("logg",t.message.toString())
            }

        })
    }
    fun observerCategoriesLiveData():LiveData<List<Order>>{
        return ordersLiveData
    }
}