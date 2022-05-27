package com.example.epetrol.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epetrol.intent.FavouritesIntent
import com.example.epetrol.repo.AppRepo
import com.example.epetrol.room.GasStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesScreenViewModel @Inject constructor(
    private val appRepo: AppRepo
) : ViewModel() {
    val favouriteGasStationsFlow = appRepo.favouriteGasStationsFlow

    fun onIntent(intent: FavouritesIntent) {
        when(intent) {
            is FavouritesIntent.ChangeGasStationFavouriteState -> {
                changeGasStationFavouriteState(intent.gasStation)
            }
        }
    }

    private fun changeGasStationFavouriteState(gasStation: GasStation) = viewModelScope.launch {
        appRepo.changeGasStationFavouriteState(gasStation)
    }
}