package com.example.epetrol.state

data class SignUpState(
    val isLoading: Boolean = false,
    val signUpEmail: String = "",
    val signUpPassword: String = ""
)
