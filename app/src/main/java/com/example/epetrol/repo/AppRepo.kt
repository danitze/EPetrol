package com.example.epetrol.repo

import android.location.Location
import com.example.epetrol.data.Coordinates
import com.example.epetrol.room.GasStation
import com.example.epetrol.services.RegionGasStationsService
import com.example.epetrol.services.GeoService
import com.example.epetrol.services.RoomService
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class AppRepo @Inject constructor(
    private val gasStationsService: RegionGasStationsService,
    private val geoService: GeoService,
    private val roomService: RoomService,
) {
    val favouriteGasStationsFlow = roomService.favouriteGasStationsFlow

    suspend fun getGasStations(region: String) = gasStationsService
        .getGasStations(region)

    suspend fun addGasStationToFavourites(gasStation: GasStation) =
        roomService.addGasStationToFavourites(gasStation)

    suspend fun removeGasStationFromFavourites(gasStation: GasStation) =
        roomService.removeGasStationFromFavourites(gasStation)

    suspend fun isGasStationFavourite(gasStation: GasStation): Boolean =
        roomService.isGasStationFavourite(gasStation)

    fun getLastLocation(): Task<Location> = geoService.getLastLocation()

    fun getAdminArea(coordinates: Coordinates): String? = geoService.getAdminArea(coordinates)
        ?: geoService.getSubAdminArea(coordinates)
}