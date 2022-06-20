package com.example.epetrol.service.abstraction

import android.location.Address
import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task

interface GeoService {
    fun getLastLocation(): Task<Location>
    fun getAdminArea(coordinates: LatLng): String?
    fun getSubAdminArea(coordinates: LatLng): String?
    fun getNearestGasStations(
        gasStationName: String,
        maxResults: Int,
        coordinates: LatLng,
        radius: Double
    ): List<Address>
}