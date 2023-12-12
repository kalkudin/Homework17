package com.example.homework17.network

import com.example.homework17.common.LoginApiService
import com.example.homework17.common.RegisterApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {
    private const val BASE_URL = "https://reqres.in/api/"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val loginApiService: LoginApiService = retrofit.create(LoginApiService::class.java)

    val registerApiService: RegisterApiService = retrofit.create(RegisterApiService::class.java)
}
