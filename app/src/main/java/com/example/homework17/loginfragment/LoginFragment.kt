package com.example.homework17.loginfragment

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homework17.R
import com.example.homework17.basefragment.BaseFragment
import com.example.homework17.common.AuthResult
import com.example.homework17.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun setUp() {
        // Your setup code
    }

    override fun setUpListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Perform login
                loginViewModel.login(email, password)
            } else {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun setUpViewActionListeners() {
        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.loginResult.collect { result ->
                when (result) {
                    is AuthResult.Success -> {
                        val token = result.data.token
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show()
                    }
                    is AuthResult.Error -> {
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                    }
                }
            }
        }
    }
}
