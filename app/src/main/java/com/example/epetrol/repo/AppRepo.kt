package com.example.epetrol.repo

import com.example.epetrol.data.GasStationInfo
import com.example.epetrol.data.RegionGasStation
import com.example.epetrol.result.AdminAreaResult
import com.example.epetrol.result.ApiResult
import com.example.epetrol.room.GasStation
import kotlinx.coroutines.flow.Flow

interface AppRepo {
    val favouriteGasStationsFlow: Flow<List<GasStation>>

    suspend fun getGasStations(region: String): ApiResult<List<RegionGasStation>>

    suspend fun getGasStationInfo(gasStationId: String): ApiResult<GasStationInfo>

    suspend fun changeGasStationFavouriteState(gasStation: GasStation)

    suspend fun isGasStationFavourite(gasStation: GasStation): Boolean

    suspend fun getAdminArea(): AdminAreaResult
}