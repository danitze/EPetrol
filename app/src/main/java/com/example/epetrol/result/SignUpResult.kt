package com.example.epetrol.result

sealed class SignUpResult {
    object SignedUp : SignUpResult()
    data class Error(val msg: String) : SignUpResult()
}
