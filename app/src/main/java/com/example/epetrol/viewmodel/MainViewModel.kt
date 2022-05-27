package com.example.epetrol.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epetrol.intent.MainIntent
import com.example.epetrol.repo.AuthRepo
import com.example.epetrol.result.SignOutResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepo: AuthRepo,
) : ViewModel() {
    private val _signOutResultsFlow = MutableSharedFlow<SignOutResult>(replay = 1)
    val signOutResultsFlow = _signOutResultsFlow.asSharedFlow()

    val tokensFlow = authRepo.tokensFlow

    fun onIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.SignOut -> signOut()
        }
    }

    private fun signOut() = viewModelScope.launch {
        val result = authRepo.signOut()
        _signOutResultsFlow.emit(result)
    }
}