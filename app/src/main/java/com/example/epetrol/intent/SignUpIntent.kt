package com.example.epetrol.intent

sealed class SignUpIntent {
    data class SignUpEmailChanged(val value: String) : SignUpIntent()
    data class SignUpPasswordChanged(val value: String) : SignUpIntent()
    object SignUp : SignUpIntent()
}
