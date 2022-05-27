package com.example.epetrol.repo

import android.location.Location
import com.example.epetrol.createTokenHeader
import com.example.epetrol.data.Coordinates
import com.example.epetrol.data.GasStationInfo
import com.example.epetrol.data.RegionGasStation
import com.example.epetrol.getExceptionMessage
import com.example.epetrol.result.ApiResult
import com.example.epetrol.room.GasStation
import com.example.epetrol.service.*
import com.google.android.gms.tasks.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepoImpl @Inject constructor(
    private val gasStationsService: RegionGasStationsService,
    private val gasStationInfoService: GasStationInfoService,
    private val geoService: GeoService,
    private val roomService: RoomService,
    private val tokenStorageService: TokenStorageService
) : AppRepo {
    override val favouriteGasStationsFlow = roomService.favouriteGasStationsFlow

    override suspend fun getGasStations(region: String): ApiResult<List<RegionGasStation>> {
        val token = tokenStorageService.getToken()
        return try {
            val result = gasStationsService.getGasStations(region, createTokenHeader(token))
            ApiResult.Data(result)
        } catch (e: Exception) {
            ApiResult.Error(e.getExceptionMessage())
        }
    }

    override suspend fun getGasStationInfo(gasStationId: String): ApiResult<GasStationInfo> {
        val token = tokenStorageService.getToken()
        return try {
            val result =
                gasStationInfoService.getGasStationInfo(gasStationId, createTokenHeader(token))
            ApiResult.Data(result)
        } catch (e: Exception) {
            ApiResult.Error(e.getExceptionMessage())
        }
    }

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