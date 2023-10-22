package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.model.Food
import com.example.foodapp.retrofit.RetrofitClient

class FoodDetailViewModel(): ViewModel() {

    val foodLiveData = MutableLiveData<Food>()

    fun getFoodById(id: Int){
        RetrofitClient.api.getFoodById(id).enqueue(object : retrofit2.Callback<com.example.foodapp.model.Food>{
            override fun onResponse(call: retrofit2.Call<Food>, response: retrofit2.Response<Food>) {
                response.body()?.let {food ->
                    foodLiveData.postValue(food)
                }
            }

            override fun onFailure(call: retrofit2.Call<com.example.foodapp.model.Food>, t: Throwable) {
                Log.e("logg",t.message.toString())
            }

        })
    }

    fun observerFoodLiveData(): MutableLiveData<Food> {
        return foodLiveData
    }
}