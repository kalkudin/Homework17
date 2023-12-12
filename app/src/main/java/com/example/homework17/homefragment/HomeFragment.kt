package com.example.homework17.homefragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.example.homework17.R
import com.example.homework17.basefragment.BaseFragment
import com.example.homework17.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    }

    override fun setUp() {
        // Your setup code
    }

    override fun setUpListeners() {
        binding.btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    override fun setUpViewActionListeners() {
        if (!isUserLoggedIn()) {
            navigateToLoginFragment()
        }
    }

    private fun isUserLoggedIn(): Boolean {
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)

        return !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }

    private fun logoutUser() {
        // Clear saved credentials
        sharedPreferences.edit().clear().apply()

        // Navigate back to LoginFragment
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }

    private fun navigateToLoginFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }
}
