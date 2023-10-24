package com.example.foodapp.ui.fragments

import android.os.Bundle
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
import io.paperdb.Paper


class CartFragment : Fragment() {

    private lateinit var binding:FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartList: MutableList<Cart>

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
            }

        })


        cartAdapter.setDataCart(cartList as ArrayList<Cart>)
        binding.rvCart.adapter = cartAdapter
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

    }

}