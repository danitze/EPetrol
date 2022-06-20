package com.example.epetrol.service.abstraction

import kotlinx.coroutines.flow.Flow

interface TokenStorageService {
    val tokensFlow: Flow<String>
    suspend fun saveToken(userToken: String)
    suspend fun clearToken()
    suspend fun getToken(): String
}