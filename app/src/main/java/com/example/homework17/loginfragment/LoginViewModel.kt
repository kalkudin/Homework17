package com.example.homework17.loginfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework17.common.ApiResponse
import com.example.homework17.network.ApiClient.loginApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableStateFlow<ApiResponse<TokenResponse>>(ApiResponse.Error(errorMessage = "Initial state"))
    val loginResult: StateFlow<ApiResponse<TokenResponse>> get() = _loginResult

    fun login(email: String, password: String) {
        if (isValidEmail(email) && password.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    val response = loginApiService.login(LoginData(email, password))
                    _loginResult.value = when (response) {
                        is ApiResponse.Success -> ApiResponse.Success(response.data)
                        is ApiResponse.Error -> ApiResponse.Error(errorMessage = response.errorMessage)
                    }
                } catch (e: Exception) {
                    _loginResult.value = ApiResponse.Error(errorMessage = e.message ?: "An unexpected error occurred")
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email == "eve.holt@reqres.in"
    }
}


