package com.example.homework17.loginfragment

import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework17.R
import com.example.homework17.basefragment.BaseFragment
import com.example.homework17.common.AuthData
import com.example.homework17.common.AuthResult
import com.example.homework17.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import androidx.core.widget.addTextChangedListener



class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    }

    override fun setUp() {
        // Your setup code
    }

    override fun setUpListeners() {
        // Initially, disable the login button
        binding.btnLogin.isEnabled = false
        binding.etEmail.addTextChangedListener { email ->
            binding.btnLogin.isEnabled = isValidEmail(email.toString()) && binding.etPassword.text!!.isNotEmpty()
        }

        binding.etPassword.addTextChangedListener { password ->
            if (password != null) {
                binding.btnLogin.isEnabled = password.isNotEmpty() && isValidEmail(binding.etEmail.text.toString())
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            loginViewModel.login(email, password)
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun setUpViewActionListeners() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginResult.collect { result ->
                    when (result) {
                        is AuthResult.Success -> {
                            val token = result.data.token
                            Toast.makeText(
                                requireContext(),
                                "Login successful!",
                                Toast.LENGTH_SHORT
                            ).show()

                            navigateToHomeFragment()
                        }

                        is AuthResult.Error -> {
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        else -> {
                        }
                    }
                }
            }
        }

        setFragmentResultListener("REGISTER_SUCCESS") { _, bundle ->
            val authData = bundle.getParcelable("authData", AuthData::class.java)
            if (authData != null) {
                binding.etEmail.setText(authData.email)
                binding.etPassword.setText(authData.password)
            }
        }
    }

    private fun navigateToHomeFragment() {
        val bundle = Bundle().apply {
            putString("user_email", binding.etEmail.text.toString())
        }
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment, bundle)
    }

    // Email validation function using Patterns.EMAIL_ADDRESS
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}