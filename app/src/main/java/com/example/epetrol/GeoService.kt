package com.example.epetrol

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import java.util.*

@SuppressLint("MissingPermission")
class GeoService(context: Context) {
    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val geoCoder by lazy { Geocoder(context, Locale.getDefault()) }


    fun getLastLocation(): Task<Location> = fusedLocationClient.lastLocation

    fun getAdminArea(coordinates: Coordinates): String? = geoCoder
        .getFromLocation(coordinates.latitude, coordinates.longitude, 1)[0]
        .adminArea

    fun getSubAdminArea(coordinates: Coordinates): String? = geoCoder
        .getFromLocation(coordinates.latitude, coordinates.longitude, 1)[0]
        .subAdminArea
}