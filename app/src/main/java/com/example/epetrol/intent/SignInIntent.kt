package com.example.epetrol.intent

sealed class SignInIntent {
    data class SignInEmailChanged(val value: String) : SignInIntent()
    data class SignInPasswordChanged(val value: String) : SignInIntent()
    object SignIn : SignInIntent()
}
