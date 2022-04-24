package com.example.epetrol.viewmodels

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epetrol.data.Coordinates
import com.example.epetrol.repo.AppRepo
import com.example.epetrol.room.GasStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepo: AppRepo,
) : ViewModel() {
    private val coordinatesFlow = MutableStateFlow<Coordinates?>(null)
    private val adminAreaFlow = coordinatesFlow.filterNotNull().map { coordinates ->
        appRepo.getAdminArea(coordinates)
    }

    val gasStationsFlow = adminAreaFlow.filterNotNull().map { adminArea ->
        appRepo.getGasStations(adminArea).body() ?: listOf()
    }

    val favouriteGasStationsFlow = appRepo.favouriteGasStationsFlow

    init {
        viewModelScope.launch(Dispatchers.Default) {
            reload()
        }
    }

    private fun reload() = viewModelScope.launch(Dispatchers.Default) {
        appRepo.getLastLocation().addOnSuccessListener { location: Location? ->
            /*coordinatesFlow.value =
                safeLet(location?.latitude, location?.longitude) { lat, lng ->
                    Coordinates(lat, lng)
                }*/
            //TODO remove mock
            coordinatesFlow.value = Coordinates(50.45466, 30.5252)
        }.addOnFailureListener {
            Log.d("MyTag", "Fails with ${it.localizedMessage}")
        }
    }

    fun changeGasStationFavouriteState(gasStation: GasStation) = viewModelScope.launch {
        if(appRepo.isGasStationFavourite(gasStation)) {
            appRepo.removeGasStationFromFavourites(gasStation)
        } else {
            appRepo.addGasStationToFavourites(gasStation)
        }
    }
}