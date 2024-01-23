package com.example.foodapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foodapp.model.User
import com.example.foodapp.retrofit.RetrofitClient
import retrofit2.Call

class ProfileViewModel(): ViewModel() {
    private var id = 0
    private var user: User = User("", "", "", "")

    private var name = ""

    fun setId(id: Int) {
        this.id = id
    }

    fun name(id: Int){

        RetrofitClient.api.getNameById(id).enqueue(object : retrofit2.Callback<String> {
            override fun onResponse(
                call: retrofit2.Call<String>,
                response: retrofit2.Response<String>
            ) {
                response.body()?.let { receivedName -> name = receivedName }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }
        })

    }

    fun getName(): String {
        return name
    }



}


