package com.example.homework17.loginfragment

import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun setUp() {
        // Your setup code
    }
    //the session is being saved along with the relevant data, but for some reason it is not loading. the email and token are being saved just fine though
    //work on a fix along the weekend
    override fun setUpListeners() {
        // Initially, disable the login button
        // I dont like the login button enabled funtionality.
        binding.btnLogin.isEnabled = false
        binding.etEmail.addTextChangedListener { email ->
            binding.btnLogin.isEnabled =
                isValidEmail(email.toString()) && binding.etPassword.text!!.isNotEmpty()
        }

        binding.etPassword.addTextChangedListener { password ->
            if (password != null) {
                binding.btnLogin.isEnabled =
                    password.isNotEmpty() && isValidEmail(binding.etEmail.text.toString())
            }
        }
        //when cleaning this up use scope functions.
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val rememberMe = binding.btnRememberMe.isChecked

            loginViewModel.login(email, password, rememberMe)
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
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    // Email validation function using Patterns.EMAIL_ADDRESS
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}