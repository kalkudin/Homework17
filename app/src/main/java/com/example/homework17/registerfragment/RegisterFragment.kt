package com.example.homework17.registerfragment

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework17.R
import com.example.homework17.basefragment.BaseFragment
import com.example.homework17.common.AuthData
import com.example.homework17.common.AuthResult
import com.example.homework17.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun setUp() {
        // Your setup code
    }

    override fun setUpListeners() {
        binding.btnRegister.setOnClickListener {
            if (!arePasswordsSame(binding.etPassword.text.toString(), binding.etRepeatPassword.text.toString())) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerViewModel.register(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    override fun setUpViewActionListeners() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.registerResult.collect { result ->
                    when (result) {
                        is AuthResult.Success -> {
                            // Use Fragment Result API to send AuthData back to LoginFragment
                            setFragmentResult("REGISTER_SUCCESS", bundleOf("authData" to AuthData(binding.etEmail.text.toString(), binding.etPassword.text.toString())))

                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                            Toast.makeText(
                                requireContext(),
                                "Register successful!",
                                Toast.LENGTH_SHORT
                            ).show()
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
    }

    private fun arePasswordsSame(password1: String, password2: String): Boolean {
        return password1 == password2
    }
}
