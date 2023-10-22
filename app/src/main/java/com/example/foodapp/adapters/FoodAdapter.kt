package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.foodapp.databinding.ItemFoodBinding
import com.example.foodapp.model.Food
import com.example.foodapp.onclick.onClickInterface

class FoodAdapter(val onClick: onClickInterface):RecyclerView.Adapter<FoodAdapter.MyViewHolder>() {
    private var foodsList = ArrayList<Food>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemFoodBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return foodsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.binding.txtfoodname.text = foodsList[position].food_name
            holder.binding.txtfoodprice.text = foodsList[position].food_price.toString()
            Glide.with(holder.itemView).load(foodsList[position].food_image).into(holder.binding.imgfood)

            holder.itemView.setOnClickListener {
                onClick.onClick(position)
            }
    }

    fun setDataFood(foods: ArrayList<Food>){
        this.foodsList = foods
        notifyDataSetChanged()
    }

    class MyViewHolder(var binding: ItemFoodBinding):RecyclerView.ViewHolder(binding.root) {

    }

    fun getFoodAt(position: Int): Food {
        return foodsList[position]
    }

    fun getFoodsList(): ArrayList<Food>{
        return foodsList
    }
}