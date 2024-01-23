package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.model.Food
import com.example.foodapp.model.User
import com.example.foodapp.retrofit.RetrofitClient
import retrofit2.Call

class InfoViewModel(): ViewModel() {

    private var id = 0

    fun setId(id: Int) {
        this.id = id
    }

    val user = MutableLiveData<User>()

    fun getUser() {
        RetrofitClient.api.getUser(id).enqueue(object : retrofit2.Callback<User> {
            override fun onResponse(
                call: retrofit2.Call<User>,
                response: retrofit2.Response<User>
            ) {
                response.body()?.let { get -> user.postValue(get) }
            }


            override fun onFailure(call: Call<User>, t: Throwable) {

            }
        })
    }

    fun observerUserLiveData(): MutableLiveData<User> {
        return user
    }
}
