package com.example.epetrol.repo.abstraction

import com.example.epetrol.result.SignInResult
import com.example.epetrol.result.SignOutResult
import com.example.epetrol.result.SignUpResult
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    val tokensFlow: Flow<String>

    suspend fun signUp(email: String, password: String, confirmPassword: String): SignUpResult

    suspend fun signIn(email: String, password: String): SignInResult

    suspend fun signOut(): SignOutResult

    suspend fun updateToken(): SignInResult
}