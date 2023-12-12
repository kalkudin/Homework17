package com.example.homework17.loginfragment

import androidx.navigation.fragment.findNavController
import com.example.homework17.R
import com.example.homework17.basefragment.BaseFragment
import com.example.homework17.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override fun setUp() {
        // Your setup code
    }

    override fun setUpListeners() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun setUpViewActionListeners() {
        // Your view action setup code
    }
}
