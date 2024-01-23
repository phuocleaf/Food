package com.example.foodapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityFoodDetailBinding
import com.example.foodapp.databinding.ActivitySignUpBinding
import com.example.foodapp.viewmodel.SignUpViewModel

private lateinit var binding: ActivitySignUpBinding
private lateinit var viewModel: SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        binding.btnRegister.setOnClickListener{

            viewModel.signUp(binding.etname.text.toString(),binding.etemail.text.toString(),binding.etpassword.text.toString(),binding.etaddress.text.toString(),binding.etphone.text.toString())

            val handler = Handler(Looper.getMainLooper())

            handler.postDelayed({
                var status = viewModel.getSignUpStatus()
                if(status == 1){
                    Toast.makeText(this,"Đăng ký thành công", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LogInActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(this, "Email đã tồn tại", Toast.LENGTH_LONG).show()
                }
            }, 1000) // 2000 mili giây = 2 giây

        }
    }
}