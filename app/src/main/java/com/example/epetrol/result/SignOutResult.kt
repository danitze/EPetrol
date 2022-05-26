package com.example.epetrol.result

sealed class SignOutResult {
    object Unauthorized : SignOutResult()
    data class Error(val msg: String) : SignOutResult()
}
