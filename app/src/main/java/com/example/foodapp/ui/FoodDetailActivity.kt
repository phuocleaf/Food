package com.example.foodapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ActivityFoodDetailBinding
import com.example.foodapp.model.Cart
import com.example.foodapp.model.Food
import com.example.foodapp.viewmodel.FoodDetailViewModel
import io.paperdb.Paper


private lateinit var binding: ActivityFoodDetailBinding
private lateinit var viewModel: FoodDetailViewModel
class FoodDetailActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Paper.init(this)
        //Paper.book().delete("cart")

        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this)[FoodDetailViewModel::class.java]

        val id = intent.getIntExtra("food_id",0).toInt()
        viewModel.getFoodById(id)
        viewModel.foodLiveData.observe(this, Observer {Food ->
            binding.txtfoodname.text = Food.food_name
            binding.txtfoodprice.text = Food.food_price.toString()
            binding.txtfooddesc.text = Food.food_desc
            Glide.with(this).load(Food.food_image).into(binding.imgfood)

            binding.imgadd.setOnClickListener{
                binding.txtamount.text = (binding.txtamount.text.toString().toInt()+1).toString()
            }

            binding.imgsub.setOnClickListener{
                if(binding.txtamount.text.toString().toInt() > 1){
                    binding.txtamount.text = (binding.txtamount.text.toString().toInt()-1).toString()
                }
            }

            binding.btnaddtocart.setOnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                var cart = Cart(Food.food_id,Food.food_name,Food.food_price,Food.food_image,binding.txtamount.text.toString().toInt())

                var isExist = false

                var cartList = mutableListOf<Cart>()
                cartList = Paper.book().read("cart", mutableListOf())!!
                if(cartList.size > 0){
                    for(i in 0 until cartList.size){
                        if(cartList[i].food_id == cart.food_id){
                            isExist = true
                            cartList[i].quantity += cart.quantity
                            Paper.book().write("cart",cartList)
                            Toast.makeText(this,cartList[i].food_name +" "+ cartList[i].quantity,Toast.LENGTH_SHORT).show()
                        }
                    }
                    if(!isExist){
                        cartList.add(cart)
                        Paper.book().write("cart",cartList)
                        Toast.makeText(this,cartList.size.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    cartList.add(cart)
                    Paper.book().write("cart",cartList)
                    Toast.makeText(this,"Added to cart",Toast.LENGTH_SHORT).show()
                }
                startActivity(intent)
                finish()
            }
        })

    }
}