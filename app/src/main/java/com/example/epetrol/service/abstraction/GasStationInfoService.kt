package com.example.epetrol.service.abstraction

import com.example.epetrol.data.GasStationInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GasStationInfoService {
    @GET("api/v1/fuel-info/details")
    suspend fun getGasStationInfo(
        @Query("gasStationId") id: String,
        @Header("Authorization") token: String
    ): GasStationInfo
}