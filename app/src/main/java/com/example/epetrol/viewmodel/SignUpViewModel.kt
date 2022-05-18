package com.example.epetrol.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epetrol.intent.SignUpIntent
import com.example.epetrol.repo.AuthRepo
import com.example.epetrol.result.SignUpResult
import com.example.epetrol.state.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {
    var state by mutableStateOf(SignUpState())

    private val _signUpResultsFlow = MutableSharedFlow<SignUpResult>(replay = 1)
    val signUpResultsFlow = _signUpResultsFlow.asSharedFlow()

    fun onIntent(intent: SignUpIntent) {
        when(intent) {
            is SignUpIntent.EmailChanged -> {
                state = state.copy(email = intent.value)
            }
            is SignUpIntent.PasswordChanged -> {
                state = state.copy(password = intent.value)
            }
            is SignUpIntent.ConfirmPasswordChanged -> {
                state = state.copy(confirmPassword = intent.value)
            }
            is SignUpIntent.SignUp -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepo.signUp(
                state.email,
                state.password,
                state.confirmPassword
            )
            _signUpResultsFlow.emit(result)
            state = state.copy(isLoading = false)
        }
    }
}