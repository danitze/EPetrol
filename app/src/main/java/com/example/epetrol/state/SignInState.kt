package com.example.epetrol.state

data class SignInState(
    val isLoading: Boolean = false,
    val signInEmail: String = "",
    val signInPassword: String = ""
)
