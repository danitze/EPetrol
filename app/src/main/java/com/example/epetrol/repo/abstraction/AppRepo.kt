package com.example.epetrol.repo.abstraction

import com.example.epetrol.data.GasStationInfo
import com.example.epetrol.data.RegionGasStation
import com.example.epetrol.result.ApiResult
import com.example.epetrol.result.NullableResult
import com.example.epetrol.data.GasStation
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface AppRepo {
    val favouriteGasStationsFlow: Flow<List<GasStation>>

    suspend fun getGasStations(region: String): ApiResult<List<RegionGasStation>>

    suspend fun getGasStationInfo(gasStationId: String): ApiResult<GasStationInfo>

    suspend fun changeGasStationFavouriteState(gasStation: GasStation)

    suspend fun isGasStationFavourite(gasStation: GasStation): Boolean

    suspend fun getCoordinates(): NullableResult<LatLng>

    suspend fun getAdminArea(): NullableResult<String>

    suspend fun getAdminArea(coordinates: LatLng): NullableResult<String>
}