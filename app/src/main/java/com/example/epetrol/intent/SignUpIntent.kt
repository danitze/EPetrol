package com.example.epetrol.intent

sealed class SignUpIntent {
    data class EmailChanged(val value: String) : SignUpIntent()
    data class PasswordChanged(val value: String) : SignUpIntent()
    data class ConfirmPasswordChanged(val value: String) : SignUpIntent()
    object SignUp : SignUpIntent()
}
