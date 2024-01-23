package com.example.foodapp.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentCartBinding
import com.example.foodapp.databinding.FragmentProfileBinding
import com.example.foodapp.ui.InfomationActivity
import com.example.foodapp.ui.LogInActivity
import com.example.foodapp.ui.MainActivity
import com.example.foodapp.ui.OrdersActivity
import com.example.foodapp.ui.SignUpActivity
import com.example.foodapp.viewmodel.ProfileViewModel
import io.paperdb.Paper


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        Paper.init(requireContext())
        val userId = Paper.book().read("user_id", 0)
        profileViewModel = ProfileViewModel()
        if (userId == -1) {
            binding.tvNotLogin.visibility = View.VISIBLE
            binding.btnRegister.visibility = View.VISIBLE
            binding.btnLogin.visibility = View.VISIBLE
            binding.tvUserName.visibility = View.GONE
            binding.btnLogout.visibility = View.GONE
            binding.llOrderDelivered.visibility = View.GONE
            binding.llOrderProcessing.visibility = View.GONE
            binding.llOrderDelivery.visibility = View.GONE
            binding.lluserinfomation.visibility = View.GONE
            guestView()
        } else {
            if (userId != null) {
                profileViewModel.name(userId)
                binding.tvNotLogin.visibility = View.GONE
                binding.btnRegister.visibility = View.GONE
                binding.btnLogin.visibility = View.GONE
                binding.tvUserName.visibility = View.VISIBLE
                binding.tvUserName.text = ""
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({

                    binding.tvUserName.text = profileViewModel.getName()
                }, 500) //
                binding.btnLogout.visibility = View.VISIBLE
                binding.btnLogout.setOnClickListener {

                    Paper.book().write("user_id", -1)
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                }

                binding.lluserinfomation.visibility = View.VISIBLE
                binding.llOrderDelivered.visibility = View.VISIBLE
                binding.llOrderProcessing.visibility = View.VISIBLE
                binding.llOrderDelivery.visibility = View.VISIBLE

                binding.lluserinfomation.setOnClickListener{
                    val intent = Intent(requireContext(), InfomationActivity::class.java)
                    startActivity(intent)
                }

                binding.llOrderProcessing.setOnClickListener{
                    val intent = Intent(requireContext(), OrdersActivity::class.java)
                    intent.putExtra("status", 1)
                    startActivity(intent)
                }

                binding.llOrderDelivery.setOnClickListener{
                    val intent = Intent(requireContext(), OrdersActivity::class.java)
                    intent.putExtra("status", 2)
                    startActivity(intent)
                }

                binding.llOrderDelivered.setOnClickListener{
                    val intent = Intent(requireContext(), OrdersActivity::class.java)
                    intent.putExtra("status", 3)
                    startActivity(intent)
                }

            }
        }
    }


    private fun guestView() {
        binding.btnRegister.setOnClickListener {
            val intent = Intent(requireContext(), SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            val intent = Intent(requireContext(), LogInActivity::class.java)
            startActivity(intent)
        }
    }
}