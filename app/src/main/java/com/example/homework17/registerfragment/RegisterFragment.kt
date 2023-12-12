package com.example.homework17.registerfragment

import androidx.navigation.fragment.findNavController
import com.example.homework17.R
import com.example.homework17.basefragment.BaseFragment
import com.example.homework17.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    override fun setUp() {
        // Your setup code
    }

    override fun setUpListeners() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun setUpViewActionListeners() {
        // Your view action setup code
    }
}
