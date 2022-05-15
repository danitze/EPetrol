package com.example.epetrol.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.epetrol.intent.SignInIntent
import com.example.epetrol.state.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(SignInState())

    fun onIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.SignInEmailChanged -> {
                state = state.copy(signInEmail = intent.value)
            }
            is SignInIntent.SignInPasswordChanged -> {
                state = state.copy(signInPassword = intent.value)
            }
            is SignInIntent.SignIn -> {

            }
        }
    }
}