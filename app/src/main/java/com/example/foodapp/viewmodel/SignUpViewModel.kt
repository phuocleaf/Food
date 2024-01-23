package com.example.foodapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foodapp.retrofit.RetrofitClient

class SignUpViewModel():ViewModel() {
    private var signUpStatus: Int = 0

    fun signUp(name: String, email: String, password: String,address:String, phone: String) {
        RetrofitClient.api.registerUser(name, email, password,address, phone).enqueue(object : retrofit2.Callback<Int> {
            override fun onResponse(call: retrofit2.Call<Int>, response: retrofit2.Response<Int>) {
                response.body()?.let { status ->
                    signUpStatus = status
                }
            }

            override fun onFailure(call: retrofit2.Call<Int>, t: Throwable) {
                signUpStatus = -1
            }

        })
    }

    fun getSignUpStatus(): Int {
        return signUpStatus
    }
}