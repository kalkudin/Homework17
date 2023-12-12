package com.example.homework17.common

import com.example.homework17.registerfragment.RegisterRequest
import com.example.homework17.registerfragment.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}