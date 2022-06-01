package com.example.epetrol.data

import com.google.android.gms.maps.model.LatLng

data class GasStationMarker(
    val stationName: String,
    val stationId: String,
    val coordinates: LatLng
)
