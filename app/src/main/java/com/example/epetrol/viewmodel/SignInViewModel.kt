package com.example.epetrol.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epetrol.intent.SignInIntent
import com.example.epetrol.repo.AuthRepo
import com.example.epetrol.result.SignInResult
import com.example.epetrol.state.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {
    var state by mutableStateOf(SignInState())

    private val _signInResultsFlow = MutableSharedFlow<SignInResult>(replay = 1)
    val signInResultsFlow = _signInResultsFlow.asSharedFlow()

    init {
        updateToken()
    }

    fun onIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.EmailChanged -> {
                state = state.copy(email = intent.value)
            }
            is SignInIntent.PasswordChanged -> {
                state = state.copy(password = intent.value)
            }
            is SignInIntent.SignIn -> {
                signIn()
            }
        }
    }

    private fun updateToken() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepo.updateToken()
            _signInResultsFlow.emit(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepo.signIn(
                state.email,
                state.password
            )
            _signInResultsFlow.emit(result)
            state = state.copy(isLoading = false)
        }
    }
}