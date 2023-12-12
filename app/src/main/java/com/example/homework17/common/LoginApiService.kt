package com.example.homework17.common

import com.example.homework17.loginfragment.LoginData
import com.example.homework17.loginfragment.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("login")
    suspend fun login(@Body request: LoginData): ApiResponse<TokenResponse>
}