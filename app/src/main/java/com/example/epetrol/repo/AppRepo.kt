package com.example.epetrol.repo

import android.location.Location
import com.example.epetrol.data.Coordinates
import com.example.epetrol.room.FavouriteGasStation
import com.example.epetrol.services.GasStationsService
import com.example.epetrol.services.GeoService
import com.example.epetrol.services.RoomService
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class AppRepo @Inject constructor(
    private val gasStationsService: GasStationsService,
    private val geoService: GeoService,
    private val roomService: RoomService,
) {
    val favouriteGasStationsFlow = roomService.favouriteGasStationsFlow

    suspend fun getGasStations(region: String) = gasStationsService
        .getGasStations(region)

    suspend fun addGasStationToFavourites(gasStation: FavouriteGasStation) =
        roomService.addGasStationToFavourites(gasStation)

    suspend fun removeGasStationFromFavourites(gasStation: FavouriteGasStation) =
        roomService.removeGasStationFromFavourites(gasStation)

    suspend fun isGasStationFavourite(gasStation: FavouriteGasStation): Boolean =
        roomService.isGasStationFavourite(gasStation)

    fun getLastLocation(): Task<Location> = geoService.getLastLocation()

    fun getAdminArea(coordinates: Coordinates): String? = geoService.getAdminArea(coordinates)
        ?: geoService.getSubAdminArea(coordinates)
}