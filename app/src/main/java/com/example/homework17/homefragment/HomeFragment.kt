package com.example.homework17.homefragment

import androidx.navigation.fragment.findNavController
import com.example.homework17.R
import com.example.homework17.basefragment.BaseFragment
import com.example.homework17.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun setUp() {
        // Your setup code
    }

    override fun setUpListeners() {
        binding.btnLogout.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    override fun setUpViewActionListeners() {
        // Your view action setup code
    }
}
