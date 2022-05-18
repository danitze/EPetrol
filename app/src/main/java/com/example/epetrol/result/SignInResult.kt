package com.example.epetrol.result

sealed class SignInResult {
    object Authorized : SignInResult()
    object Unauthorized : SignInResult()
    data class Error(val msg: String) : SignInResult()
}
