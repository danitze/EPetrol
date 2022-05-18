package com.example.epetrol.intent

sealed class SignInIntent {
    data class EmailChanged(val value: String) : SignInIntent()
    data class PasswordChanged(val value: String) : SignInIntent()
    object SignIn : SignInIntent()
}
