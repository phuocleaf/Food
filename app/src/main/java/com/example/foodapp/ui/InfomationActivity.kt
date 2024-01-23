package com.example.foodapp.ui

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityInfomationBinding
import com.example.foodapp.viewmodel.InfoViewModel
import io.paperdb.Paper

private lateinit var binding: ActivityInfomationBinding
private lateinit var viewModel: InfoViewModel

class InfomationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfomationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[InfoViewModel::class.java]

        Paper.init(this)
        var id = Paper.book().read("user_id", 0)
        id?.let { viewModel.setId(it) }
        viewModel.getUser()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            initView()
        }, 50)
    }

    private fun initView() {
        var user = viewModel.observerUserLiveData().value
        if (user != null) {
            binding.tvUserName.text = user.user_name
            binding.tvUserEmail.text = user.user_email
            binding.tvUserPhone.text = user.user_phone
            binding.tvUserAddress.text = user.user_address
        }
    }
}