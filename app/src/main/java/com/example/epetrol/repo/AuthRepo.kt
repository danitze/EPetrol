package com.example.epetrol.repo

import com.example.epetrol.result.AuthResult

interface AuthRepo {
    suspend fun signUp(email: String, password: String, repeatedPassword: String): AuthResult

    suspend fun signIn(email: String, password: String): AuthResult

    suspend fun updateToken(token: String): AuthResult
}