package com.example.epetrol.state

data class SignInState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = ""
)
