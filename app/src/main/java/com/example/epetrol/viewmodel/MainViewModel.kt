package com.example.epetrol.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epetrol.data.Coordinates
import com.example.epetrol.intent.MainIntent
import com.example.epetrol.repo.AppRepo
import com.example.epetrol.repo.AuthRepo
import com.example.epetrol.result.SignOutResult
import com.example.epetrol.room.GasStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepo: AppRepo,
    private val authRepo: AuthRepo,
) : ViewModel() {
    private val coordinatesFlow = MutableStateFlow<Coordinates?>(null)
    private val adminAreaFlow = coordinatesFlow.filterNotNull().map { coordinates ->
        appRepo.getAdminArea(coordinates)
    }

    val gasStationsFlow = adminAreaFlow.filterNotNull().map { adminArea ->
        appRepo.getGasStations(adminArea).body() ?: listOf()
    }

    val favouriteGasStationsFlow = appRepo.favouriteGasStationsFlow

    private val _signOutResultsFlow = MutableSharedFlow<SignOutResult>(replay = 1)
    val signOutResultsFlow = _signOutResultsFlow.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            reload()
        }
    }

    fun onIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.ChangeGasStationFavouriteState -> changeGasStationFavouriteState(
                gasStation = intent.gasStation
            )
            is MainIntent.SignOut -> signOut()
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

    private fun changeGasStationFavouriteState(gasStation: GasStation) = viewModelScope.launch {
        if(appRepo.isGasStationFavourite(gasStation)) {
            appRepo.removeGasStationFromFavourites(gasStation)
        } else {
            appRepo.addGasStationToFavourites(gasStation)
        }
    }

    private fun signOut() = viewModelScope.launch {
        val result = authRepo.signOut()
        _signOutResultsFlow.emit(result)
    }
}