package com.example.foodapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.adapters.FoodAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.model.Food
import com.example.foodapp.onclick.onClickInterface
import com.example.foodapp.ui.FoodDetailActivity
import com.example.foodapp.viewmodel.HomeViewModel
import java.util.Locale

///clone

class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var foodAdapter: FoodAdapter
    private var foodsList = ArrayList<Food>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        viewModel.getCategories()
        observerLiveData()
        //
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })


    }

    private fun filterList(query: String?) {
        if(query != null ) {
            foodsList = foodAdapter.getFoodsList()
            val filterList = ArrayList<Food>()
            for (i in foodsList) {
                if (i.food_name.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                    filterList.add(i)
                }
            }
            if (filterList.isEmpty()) {

            } else {
                foodAdapter.setDataFood(filterList)
            }
        }

    }

    private fun initView() {
        foodAdapter = FoodAdapter(object: onClickInterface{
            override fun onClick(position: Int) {
                val food = foodAdapter.getFoodAt(position)
                var intent: Intent = Intent(context, FoodDetailActivity::class.java)
                intent.putExtra("food_id",food.food_id)
                startActivity(intent)
            }

        })

        binding.recyclercate.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = foodAdapter
        }
    }

    private fun observerLiveData() {
        viewModel.observerCategoriesLiveData().observe(viewLifecycleOwner) { foods ->
            foodAdapter.setDataFood(foods as ArrayList<Food>)
        }
    }

}

