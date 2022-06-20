package com.example.epetrol.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epetrol.intent.ListIntent
import com.example.epetrol.repo.abstraction.AppRepo
import com.example.epetrol.data.GasStation
import com.example.epetrol.state.ListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val appRepo: AppRepo
) : ViewModel() {
    val favouriteGasStationsFlow = appRepo.favouriteGasStationsFlow

    var listScreenState by mutableStateOf(ListScreenState())

    init {
        viewModelScope.launch(Dispatchers.Default) {
            reload()
        }
    }

    fun onIntent(intent: ListIntent) {
        when (intent) {
            is ListIntent.ChangeGasStationFavouriteState -> {
                changeGasStationFavouriteState(intent.gasStation)
            }
        }
    }

    private fun reload() = viewModelScope.launch {
        withContext(Dispatchers.Default) { appRepo.getAdminArea() }
            .onData { adminArea ->
                appRepo.getGasStations(adminArea).onData { regionGasStations ->
                    listScreenState = listScreenState.copy(
                        data = regionGasStations,
                        error = null
                    )
                }.onError { msg ->
                    listScreenState = listScreenState.copy(error = msg)
                }
            }.onError { msg ->
                listScreenState = listScreenState.copy(error = msg)
            }.onNull {

            }
    }

    private fun changeGasStationFavouriteState(gasStation: GasStation) = viewModelScope.launch {
        appRepo.changeGasStationFavouriteState(gasStation)
    }
}