package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.model.Food
import com.example.foodapp.model.foods
import com.example.foodapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel(){
    private var foodsLiveData = MutableLiveData<List<Food>>()
    fun getCategories(){
        RetrofitClient.api.getFoods().enqueue(object : Callback<foods>{
            override fun onResponse(call: Call<foods>, response: Response<foods>) {
                response.body()?.let {categories1 ->
                    foodsLiveData.postValue(categories1.foods)
                }
            }

            override fun onFailure(call: Call<foods>, t: Throwable) {
                Log.e("logg",t.message.toString())
            }

        })
    }
    fun observerCategoriesLiveData():LiveData<List<Food>>{
        return foodsLiveData
    }
}