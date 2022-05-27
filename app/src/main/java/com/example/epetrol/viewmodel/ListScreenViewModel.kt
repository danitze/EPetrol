package com.example.epetrol.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epetrol.intent.ListIntent
import com.example.epetrol.repo.AppRepo
import com.example.epetrol.result.AdminAreaResult
import com.example.epetrol.result.ApiResult
import com.example.epetrol.room.GasStation
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
        val result = withContext(Dispatchers.Default) {
            appRepo.getAdminArea()
        }
        when(result) {
            is AdminAreaResult.Success -> {
                val gasStationsResult = appRepo.getGasStations(result.adminArea)
                listScreenState = when(gasStationsResult) {
                    is ApiResult.Data -> {
                        listScreenState.copy(
                            data = gasStationsResult.data,
                            error = null
                        )
                    }
                    is ApiResult.Error -> {
                        listScreenState.copy(error = gasStationsResult.msg)
                    }
                }
            }
            is AdminAreaResult.Null -> {}
            is AdminAreaResult.Error -> {
                listScreenState = listScreenState.copy(error = result.msg)
            }
        }
    }

    private fun changeGasStationFavouriteState(gasStation: GasStation) = viewModelScope.launch {
        appRepo.changeGasStationFavouriteState(gasStation)
    }
}