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

    //this currently doesnt work because the viewmodel only saves the data if there the checkbox is checked otherwise we wont get anything
    //on the display because nothng is getting saved. its an easy fix but i am sleepy now.
    override fun setUpListeners() {
        // Initially, disable the login button
        //the data is being saved, but i cant seem to save the session.
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

        binding.btnLogin.setOnClickListener {
            //use scope fucntions here
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            loginViewModel.login(email, password)
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    //i think the reaspon why nothing is being saved is because i dont have my object declared as a singleton, so there is a new state every time
    //the app loads up.
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
                            ).show()//this is being done in the viewmodel, just access the object and write in it directly
                            if(binding.btnRememberMe.isChecked){
                                loginViewModel.saveUserInfo(binding.etEmail.text.toString(), token)
                                navigateToHomeFragment()
                            }
//                            navigateToHomeFragment()
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