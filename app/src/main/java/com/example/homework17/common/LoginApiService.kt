package com.example.homework17.common

import com.example.homework17.loginfragment.LoginRequest
import com.example.homework17.loginfragment.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}