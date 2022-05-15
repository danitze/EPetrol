package com.example.epetrol.service

import com.example.epetrol.data.SignInRequest
import com.example.epetrol.data.SignUpRequest
import com.example.epetrol.data.AuthResponse
import com.example.epetrol.data.TokenResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("api/v1/security/register")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): AuthResponse

    @POST("api/v1/security/login")
    suspend fun signIn(@Body signInRequest: SignInRequest): TokenResponse

    @POST("api/v1/security/update-token")
    suspend fun updateToken(@Header("Authorization") authHeader: String): TokenResponse
}