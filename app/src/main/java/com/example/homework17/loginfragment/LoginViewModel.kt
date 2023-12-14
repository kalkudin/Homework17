package com.example.homework17.loginfragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework17.common.AuthResult
import com.example.homework17.common.PreferencesRepository
import com.example.homework17.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableStateFlow<AuthResult<LoginResponse>?>(null)
    val loginResult: StateFlow<AuthResult<LoginResponse>?> get() = _loginResult

    fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            try {
                val response = ApiClient.loginApiService.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    _loginResult.value =
                        AuthResult.Success(loginResponse ?: LoginResponse("blank token"))

                    // Save email and token to preferences if "Remember Me" is checked
                    if (rememberMe) {
                        PreferencesRepository.writeEmailAndToken(email, loginResponse?.token ?: "")
                    }
                } else {
                    _loginResult.value =
                        AuthResult.Error("Login failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _loginResult.value = AuthResult.Error("An error occurred: ${e.message}")
                Log.e("LoginViewModel", "An error occurred during login", e)
            }
        }
    }
}






