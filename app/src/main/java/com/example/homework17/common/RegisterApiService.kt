package com.example.homework17.common

import com.example.homework17.registerfragment.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): ApiResponse<RegisterRequest>
}