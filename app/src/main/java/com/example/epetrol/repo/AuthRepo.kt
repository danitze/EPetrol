package com.example.epetrol.repo

import com.example.epetrol.result.SignInResult
import com.example.epetrol.result.SignOutResult
import com.example.epetrol.result.SignUpResult

interface AuthRepo {
    suspend fun signUp(email: String, password: String, confirmPassword: String): SignUpResult

    suspend fun signIn(email: String, password: String): SignInResult

    suspend fun signOut(): SignOutResult

    suspend fun updateToken(): SignInResult
}