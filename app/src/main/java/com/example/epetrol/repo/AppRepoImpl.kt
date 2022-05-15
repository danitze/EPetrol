package com.example.epetrol.repo

import android.location.Location
import com.example.epetrol.data.Coordinates
import com.example.epetrol.room.GasStation
import com.example.epetrol.service.GasStationInfoService
import com.example.epetrol.service.RegionGasStationsService
import com.example.epetrol.service.GeoService
import com.example.epetrol.service.RoomService
import com.google.android.gms.tasks.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepoImpl @Inject constructor(
    private val gasStationsService: RegionGasStationsService,
    private val gasStationInfoService: GasStationInfoService,
    private val geoService: GeoService,
    private val roomService: RoomService,
) : AppRepo {
    override val favouriteGasStationsFlow = roomService.favouriteGasStationsFlow

    override suspend fun getGasStations(region: String) = gasStationsService
        .getGasStations(region)

    override suspend fun getGasStationInfo(gasStationId: String) = gasStationInfoService
        .getGasStationInfo(gasStationId)

    override suspend fun addGasStationToFavourites(gasStation: GasStation) =
        roomService.addGasStationToFavourites(gasStation)

    override suspend fun removeGasStationFromFavourites(gasStation: GasStation) =
        roomService.removeGasStationFromFavourites(gasStation)

    override suspend fun isGasStationFavourite(gasStation: GasStation): Boolean =
        roomService.isGasStationFavourite(gasStation)

    override fun getLastLocation(): Task<Location> = geoService.getLastLocation()

    override fun getAdminArea(coordinates: Coordinates): String? = geoService.getAdminArea(coordinates)
        ?: geoService.getSubAdminArea(coordinates)
}