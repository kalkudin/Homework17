package com.example.homework17.homefragment

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homework17.R
import com.example.homework17.basefragment.BaseFragment
import com.example.homework17.common.PreferencesRepository
import com.example.homework17.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val preferencesRepository = PreferencesRepository

    override fun setUp() {
        // Your setup code
        displayUserEmail()
    }

    override fun setUpListeners() {
        binding.btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    override fun setUpViewActionListeners() {
        // ... (rest of your setup)
    }

    private fun displayUserEmail() {
        viewLifecycleOwner.lifecycleScope.launch() {
            val userEmail = preferencesRepository.readEmail()
            Log.d("LoginFragment", "User Email: ${userEmail.first()}")
            binding.tvEmail.text = userEmail.first()
        }
    }

    private fun logoutUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            preferencesRepository.clearSession()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }
}
