package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemCartBinding
import com.example.foodapp.databinding.ItemFoodBinding
import com.example.foodapp.model.Cart
import com.example.foodapp.model.Food
import com.example.foodapp.onclick.ChangeNumListener
import com.example.foodapp.onclick.onClickInterface

class CartAdapter(val changeNumListener: ChangeNumListener): RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    private var cartList = ArrayList<Cart>()

    class MyViewHolder(var binding: ItemCartBinding):RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.txtfoodname.text = cartList[position].food_name
        holder.binding.txtfoodprice.text = cartList[position].food_price.toString()
        holder.binding.txtquantity.text = cartList[position].quantity.toString()
        Glide.with(holder.itemView).load(cartList[position].food_image).into(holder.binding.imgfood)

        holder.binding.imgadd.setOnClickListener {
            changeNumListener.changeNum(position,true)
        }
        holder.binding.imgsub.setOnClickListener {
            changeNumListener.changeNum(position,false)
        }

    }

    fun getCartAt(position: Int): Cart {
        return cartList[position]
    }

    fun setDataCart(carts: ArrayList<Cart>){
        this.cartList = carts
        notifyDataSetChanged()
    }
}
