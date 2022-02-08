package com.example.epetrol

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val coordinatesFlow = MutableStateFlow<Coordinates?>(null)
    private val adminAreaFlow = MutableStateFlow<String?>(null)

    private val geoService = GeoService(application)

    init {
        viewModelScope.launch(Dispatchers.Default) {
            geoService.getLastLocation().addOnSuccessListener { location: Location? ->
                coordinatesFlow.value =
                    safeLet(location?.latitude, location?.longitude) { lat, lng ->
                        Coordinates(lat, lng)
                    }
                //TODO remove mock
                coordinatesFlow.value = Coordinates(50.45466, 30.5252)
                adminAreaFlow.value = coordinatesFlow.value?.let {
                    geoService.getAdminArea(it) ?: geoService.getSubAdminArea(it)
                }
            }.addOnFailureListener {
                Log.d("MyTag", "Fails with ${it.localizedMessage}")
            }
        }
    }

}