package com.example.homework17.common

sealed class ApiResponse<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String? = null) : ApiResponse<Nothing>()
}

