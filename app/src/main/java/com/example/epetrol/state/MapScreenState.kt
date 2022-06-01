package com.example.epetrol.state

import com.example.epetrol.data.GasStationMarker
import com.google.android.gms.maps.model.LatLng

data class MapScreenState(
    val isLoading: Boolean = false,
    val markers: List<GasStationMarker> = listOf(),
    val coordinates: LatLng = LatLng(50.45, 30.52),
    val error: String? = null
)
