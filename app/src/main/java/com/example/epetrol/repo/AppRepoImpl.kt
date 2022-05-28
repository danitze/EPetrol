package com.example.epetrol.repo

import com.example.epetrol.createTokenHeader
import com.example.epetrol.data.GasStationInfo
import com.example.epetrol.data.RegionGasStation
import com.example.epetrol.getExceptionMessage
import com.example.epetrol.getFormattedDate
import com.example.epetrol.result.AdminAreaResult
import com.example.epetrol.result.ApiResult
import com.example.epetrol.room.GasStation
import com.example.epetrol.service.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepoImpl @Inject constructor(
    private val gasStationsService: RegionGasStationsService,
    private val gasStationInfoService: GasStationInfoService,
    private val geoService: GeoService,
    private val roomService: RoomService,
    private val tokenStorageService: TokenStorageService,
    private val gasStationsStorageService: GasStationsStorageService
) : AppRepo {
    override val favouriteGasStationsFlow = roomService.favouriteGasStationsFlow

    override suspend fun getGasStations(region: String): ApiResult<List<RegionGasStation>> {
        val token = tokenStorageService.getToken()
        if(gasStationsDateChanged()) {
            gasStationsStorageService.clearPrefs()
        }
        return try {
            var result = gasStationsStorageService.getRegionGasStations(region)
            if(result.isEmpty()) {
                result = gasStationsService.getGasStations(region, createTokenHeader(token))
                gasStationsStorageService.saveRegionGasStations(region, result)
                if(gasStationsStorageService.getLastUpdateDate().isBlank()) {
                    gasStationsStorageService.saveLastUpdateDate()
                }
            }
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

    override suspend fun changeGasStationFavouriteState(gasStation: GasStation) {
        if (isGasStationFavourite(gasStation)) {
            removeGasStationFromFavourites(gasStation)
        } else {
            addGasStationToFavourites(gasStation)
        }
    }

    override suspend fun isGasStationFavourite(gasStation: GasStation): Boolean =
        roomService.isGasStationFavourite(gasStation)

    override suspend fun getAdminArea(): AdminAreaResult {
        /*try {
            val locationFlow = MutableSharedFlow<Location?>()
            geoService.getLastLocation().addOnCompleteListener {
                if(it.isSuccessful) {
                    runBlocking {
                        locationFlow.emit(it.result)
                    }
                }
            }
            val location = locationFlow.first()
            location?.let {
                val coordinates = Coordinates(
                    it.latitude,
                    it.longitude
                )
                val adminArea =
                    geoService.getAdminArea(coordinates) ?: geoService.getSubAdminArea(coordinates)
                    ?: return AdminAreaResult.Null
                return AdminAreaResult.Success(adminArea)
            } ?: return AdminAreaResult.Null
        } catch (e: Exception) {
            return AdminAreaResult.Error(e.getExceptionMessage())
        }*/
        //TODO remove mock
        return AdminAreaResult.Success("Kharkivs'ka oblast")
    }

    private suspend fun addGasStationToFavourites(gasStation: GasStation) =
        roomService.addGasStationToFavourites(gasStation)

    private suspend fun removeGasStationFromFavourites(gasStation: GasStation) =
        roomService.removeGasStationFromFavourites(gasStation)

    private suspend fun gasStationsDateChanged(): Boolean {
        return getFormattedDate() == gasStationsStorageService.getLastUpdateDate()
    }
}