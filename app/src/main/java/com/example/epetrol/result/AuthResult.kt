package com.example.epetrol.result

sealed class AuthResult {
    object Authorized : AuthResult()
    object Unauthorized : AuthResult()
    data class Error(val msg: String) : AuthResult()
}
