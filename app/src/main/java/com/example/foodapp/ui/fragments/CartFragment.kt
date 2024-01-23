package com.example.foodapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapters.CartAdapter
import com.example.foodapp.databinding.FragmentCartBinding
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.model.Cart
import com.example.foodapp.onclick.ChangeNumListener
import com.example.foodapp.viewmodel.CartViewModel
import com.google.gson.Gson
import io.paperdb.Paper


class CartFragment : Fragment() {

    private lateinit var binding:FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartList: MutableList<Cart>
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        Paper.init(requireContext())
        cartList = Paper.book().read("cart", mutableListOf())!!
        var total = 0
        for (i in cartList){
            total += i.food_price * i.quantity
        }
        binding.txtmoney.text = total.toString()
        total = 0
        cartAdapter = CartAdapter(object : ChangeNumListener{
            override fun changeNum(position: Int, isAdd: Boolean) {
                if (isAdd){
                    cartList[position].quantity++
                }else{
                    if (cartList[position].quantity > 1){
                        cartList[position].quantity--
                    }else{
                        cartList.removeAt(position)
                        Toast.makeText(context, "da xoa san pham", Toast.LENGTH_SHORT).show()
                    }
                }
                Paper.book().write("cart",cartList)
                cartAdapter.setDataCart(cartList as ArrayList<Cart>)

                for (i in cartList){
                    total += i.food_price * i.quantity
                }
                binding.txtmoney.text = total.toString()
                total = 0
            }

        })

        cartAdapter.setDataCart(cartList as ArrayList<Cart>)
        binding.rvCart.adapter = cartAdapter
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        binding.btnbuy.setOnClickListener{
            val userId = Paper.book().read("user_id", -1)
            if (userId == -1){
                Toast.makeText(context, "Bạn cần đăng nhập để thanh toán", Toast.LENGTH_SHORT).show()
            }else if(cartList.size == 0){
                Toast.makeText(context, "Giỏ hàng trống", Toast.LENGTH_SHORT).show()
            }else{
                var cart = Gson().toJson(cartList)
                //Log.d("cart",cart)
                cartViewModel = CartViewModel()
                cartViewModel.setCartList(cartList)
                cartViewModel.setTotal(binding.txtmoney.text.toString().toInt())
                userId?.let { it1 -> cartViewModel.setId(it1) }
                cartViewModel.checkout()
                val handler = android.os.Handler(android.os.Looper.getMainLooper())
                handler.postDelayed({
                    var status = cartViewModel.getResponse()
                    if(status != -1){
                        Toast.makeText(context, "Đặt hàng thành công", Toast.LENGTH_SHORT).show()
                        Paper.book().write("cart", mutableListOf<Cart>())
                        cartAdapter.setDataCart(mutableListOf<Cart>() as ArrayList<Cart>)
                        binding.txtmoney.text = "0"
                    } else{
                        Toast.makeText(context, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show()
                    }
                }, 1000)
            }
        }

    }

}