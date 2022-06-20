package com.example.epetrol.repo.implementation

import android.location.Location
import com.example.epetrol.createTokenHeader
import com.example.epetrol.data.GasStationInfo
import com.example.epetrol.data.RegionGasStation
import com.example.epetrol.getExceptionMessage
import com.example.epetrol.getFormattedDate
import com.example.epetrol.repo.abstraction.AppRepo
import com.example.epetrol.result.ApiResult
import com.example.epetrol.result.NullableResult
import com.example.epetrol.data.GasStation
import com.example.epetrol.service.abstraction.*
import com.example.epetrol.toLatLng
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepoImpl @Inject constructor(
    private val gasStationsService: RegionGasStationsService,
    private val gasStationInfoService: GasStationInfoService,
    private val geoService: GeoService,
    private val favouriteGasStationsService: FavouriteGasStationsService,
    private val tokenStorageService: TokenStorageService,
    private val gasStationsStorageService: GasStationsStorageService
) : AppRepo {
    override val favouriteGasStationsFlow = favouriteGasStationsService.favouriteGasStationsFlow

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
        favouriteGasStationsService.isGasStationFavourite(gasStation)

    override suspend fun getCoordinates(): NullableResult<LatLng> {
        /*return try {
            getCurrentCoordinates()?.let { NullableResult.Data(it) } ?: NullableResult.Null()
        } catch (e: Exception) {
            NullableResult.Error(e.getExceptionMessage())
        }*/
        //TODO remove mock
        return NullableResult.Data(LatLng(50.45, 30.52))
    }

    override suspend fun getAdminArea(): NullableResult<String> {
        //TODO remove mock
        /*return try {
            getCurrentCoordinates()?.let { coordinates ->
                val adminArea = geoService.getAdminArea(coordinates)
                    ?: geoService.getSubAdminArea(coordinates)
                    ?: return@let NullableResult.Null()
                NullableResult.Data(adminArea)
            } ?: NullableResult.Null()
        } catch (e: Exception) {
            NullableResult.Error(e.getExceptionMessage())
        }*/
        return NullableResult.Data("Kharkivs'ka oblast")
    }

    override suspend fun getAdminArea(coordinates: LatLng): NullableResult<String> {
        try {
            val adminArea =
                geoService.getAdminArea(coordinates)
                    ?: geoService.getSubAdminArea(coordinates)
                    ?: return NullableResult.Null()
            return NullableResult.Data(adminArea)
        } catch (e: Exception) {
            return NullableResult.Error(e.getExceptionMessage())
        }
    }

    private suspend fun addGasStationToFavourites(gasStation: GasStation) =
        favouriteGasStationsService.addGasStationToFavourites(gasStation)

    private suspend fun removeGasStationFromFavourites(gasStation: GasStation) =
        favouriteGasStationsService.removeGasStationFromFavourites(gasStation)

    private suspend fun gasStationsDateChanged(): Boolean {
        return getFormattedDate() == gasStationsStorageService.getLastUpdateDate()
    }

    private suspend fun getCurrentCoordinates(): LatLng? {
        val locationFlow = MutableSharedFlow<Location?>()
        geoService.getLastLocation().addOnCompleteListener {
            if(it.isSuccessful) {
                runBlocking {
                    locationFlow.emit(it.result)
                }
            }
        }
        return locationFlow.first()?.toLatLng()
    }
}