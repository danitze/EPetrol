package com.example.epetrol.viewmodels

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epetrol.data.Coordinates
import com.example.epetrol.repo.GeoRepo
import com.example.epetrol.services.GeoService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val geoService: GeoService,
    private val geoRepo: GeoRepo
) : ViewModel() {
    private val coordinatesFlow = MutableStateFlow<Coordinates?>(null)
    private val adminAreaFlow = coordinatesFlow.filterNotNull().map { coordinates ->
        geoService.getAdminArea(coordinates) ?: geoService.getSubAdminArea(coordinates)
    }

    val stationsFlow = adminAreaFlow.filterNotNull().map { adminArea ->
        geoRepo.getFuelInfo(adminArea).body()?.let { fuelInfoList ->
            fuelInfoList.filter { it.gasStation != null }.groupBy { it.gasStation!! }
        } ?: mapOf()
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            reload()
        }

    }

    private fun reload() = viewModelScope.launch(Dispatchers.Default) {
        geoService.getLastLocation().addOnSuccessListener { location: Location? ->
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
}