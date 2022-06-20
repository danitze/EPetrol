package com.example.epetrol.service.implementation

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.example.epetrol.service.abstraction.GeoService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("MissingPermission")
@Singleton
class GeoServiceImpl @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val geoCoder: Geocoder
): GeoService {

    override fun getLastLocation(): Task<Location> = fusedLocationClient.lastLocation

    override fun getAdminArea(coordinates: LatLng): String? = geoCoder
        .getFromLocation(coordinates.latitude, coordinates.longitude, 1)[0]
        .adminArea

    override fun getSubAdminArea(coordinates: LatLng): String? = geoCoder
        .getFromLocation(coordinates.latitude, coordinates.longitude, 1)[0]
        .subAdminArea

    override fun getNearestGasStations(
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