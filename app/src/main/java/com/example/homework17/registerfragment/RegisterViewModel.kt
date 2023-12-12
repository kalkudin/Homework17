package com.example.homework17.registerfragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework17.common.AuthResult
import com.example.homework17.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _registerResult = MutableStateFlow<AuthResult<RegisterResponse>?>(null)
    val registerResult: StateFlow<AuthResult<RegisterResponse>?> get() = _registerResult

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = ApiClient.registerApiService.register(RegisterRequest(email, password))
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    _registerResult.value = AuthResult.Success(registerResponse ?: RegisterResponse(0, "blank token"))
                } else {
                    _registerResult.value = AuthResult.Error("Registration failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _registerResult.value = AuthResult.Error("An error occurred: ${e.message}")
                Log.e("RegisterViewModel", "An error occurred during registration", e)
            }
        }
    }
}