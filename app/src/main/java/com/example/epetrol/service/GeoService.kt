package com.example.epetrol.service

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("MissingPermission")
@Singleton
class GeoService @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val geoCoder: Geocoder
) {

    fun getLastLocation(): Task<Location> = fusedLocationClient.lastLocation

    fun getAdminArea(coordinates: LatLng): String? = geoCoder
        .getFromLocation(coordinates.latitude, coordinates.longitude, 1)[0]
        .adminArea

    fun getSubAdminArea(coordinates: LatLng): String? = geoCoder
        .getFromLocation(coordinates.latitude, coordinates.longitude, 1)[0]
        .subAdminArea

    fun getNearestGasStations(
        gasStationName: String,
        maxResults: Int,
        coordinates: LatLng,
        radius: Double
    ): List<Address> =
        geoCoder.getFromLocationName(
            gasStationName,
            maxResults,
            coordinates.latitude - radius,
            coordinates.longitude - radius,
            coordinates.latitude + radius,
            coordinates.longitude + radius
        )
}