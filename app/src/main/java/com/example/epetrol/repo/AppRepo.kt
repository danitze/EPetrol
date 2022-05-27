package com.example.epetrol.repo

import android.location.Location
import com.example.epetrol.data.Coordinates
import com.example.epetrol.data.GasStationInfo
import com.example.epetrol.data.RegionGasStation
import com.example.epetrol.result.ApiResult
import com.example.epetrol.room.GasStation
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AppRepo {
    val favouriteGasStationsFlow: Flow<List<GasStation>>

    suspend fun getGasStations(region: String): ApiResult<List<RegionGasStation>>

    suspend fun getGasStationInfo(gasStationId: String): ApiResult<GasStationInfo>

    suspend fun addGasStationToFavourites(gasStation: GasStation)

    suspend fun removeGasStationFromFavourites(gasStation: GasStation)

    suspend fun isGasStationFavourite(gasStation: GasStation): Boolean

    fun getLastLocation(): Task<Location>

    fun getAdminArea(coordinates: Coordinates): String?
}