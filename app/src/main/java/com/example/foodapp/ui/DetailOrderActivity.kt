package com.example.foodapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapters.DetailOrderAdapter
import com.example.foodapp.databinding.ActivityDetailOrderBinding
import com.example.foodapp.model.Detail
import com.example.foodapp.model.Order
import com.example.foodapp.viewmodel.DetailsOrderViewModel
import com.example.foodapp.viewmodel.FoodDetailViewModel

private lateinit var binding: ActivityDetailOrderBinding
private lateinit var viewModel: DetailsOrderViewModel
private lateinit var detailAdapter: DetailOrderAdapter
private var orderList = ArrayList<Detail>()

class DetailOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DetailsOrderViewModel::class.java]

        initView()


    }

    private fun initView() {
        val orderId = intent.getIntExtra("order_id", 0)
        val total = intent.getIntExtra("order_total", 0)
        val dateTime = intent.getStringExtra("order_datetime")

        binding.tvorderid.text = orderId.toString()
        binding.tvordertotal.text = total.toString()
        binding.tvordertime.text = dateTime

        viewModel.setOrderId(orderId)
        viewModel.getDetails()
        observerLiveData()
    }

    private fun observerLiveData() {
        viewModel.observerDetailsLiveData().observe(this) {
            orderList = it as ArrayList<Detail>
            initRecyclerView()
        }
    }

    private fun initRecyclerView() {
        detailAdapter = DetailOrderAdapter()
        detailAdapter.setDataDetail(orderList)
        binding.rvdetailorder.apply {
            adapter = detailAdapter
            layoutManager = LinearLayoutManager(this@DetailOrderActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

//    override fun onBackPressed() {
//        val status = intent.getIntExtra("status", 0)
//        val intent = Intent(this, OrdersActivity::class.java)
//        //var status = intent.getIntExtra("status", 0)
//        intent.putExtra("status", status)
//        startActivity(intent)
//    }
}