package com.example.epetrol.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epetrol.MAP_STATIONS_RADIUS
import com.example.epetrol.MAX_MAP_STATIONS_RESULT
import com.example.epetrol.data.GasStationMarker
import com.example.epetrol.repo.AppRepo
import com.example.epetrol.repo.MapRepo
import com.example.epetrol.state.MapScreenState
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val appRepo: AppRepo,
    private val mapRepo: MapRepo
) : ViewModel() {
    var mapScreenState by mutableStateOf(MapScreenState())

    private val coordinatesStateFlow = MutableStateFlow<LatLng?>(null)

    init {
        updateCoordinates()
        coordinatesStateFlow.filterNotNull().onEach { coordinates ->
            mapScreenState = mapScreenState.copy(
                coordinates = coordinates,
                error = null
            )
            fetchNearestGasStations(coordinates)
        }.launchIn(viewModelScope)
    }

    private fun updateCoordinates() = viewModelScope.launch {
        withContext(Dispatchers.Default) {
            appRepo.getCoordinates()
        }.onAsyncData { coordinates ->
            coordinatesStateFlow.emit(coordinates)
        }.onNull {
            mapScreenState = mapScreenState.copy(error = "Cannot find coordinates")
        }.onError { msg ->
            mapScreenState = mapScreenState.copy(error = msg)
        }
    }

    private suspend fun fetchNearestGasStations(coordinates: LatLng) {
        withContext(Dispatchers.Default) {
            appRepo.getAdminArea(coordinates)
        }.onAsyncData { adminArea ->
            val markers = mutableListOf<GasStationMarker>()
            appRepo.getGasStations(adminArea).onAsyncData { gasStations ->
                gasStations.forEach { gasStation ->
                    withContext(Dispatchers.Default) {
                        mapRepo.getNearestGasStations(
                            gasStation,
                            MAX_MAP_STATIONS_RESULT,
                            coordinates,
                            MAP_STATIONS_RADIUS
                        )
                    }.onData {
                        markers.addAll(it)
                    }.onNull {

                    }.onError {
                        //TODO log
                    }
                }
                mapScreenState = mapScreenState.copy(
                    markers = markers,
                    error = null
                )
            }.onError { msg ->
                mapScreenState = mapScreenState.copy(error = msg)
            }
        }.onNull {
            mapScreenState = mapScreenState.copy(error = "Cannot find admin area")
        }.onError { msg ->
            mapScreenState = mapScreenState.copy(error = msg)
        }
    }
}