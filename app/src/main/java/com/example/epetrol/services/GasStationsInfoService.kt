package com.example.epetrol.services

import com.example.epetrol.data.StationInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface GasStationsInfoService {
    @GET("api/v1/fuel-info?regionLatin={region}")
    suspend fun getRegionalStations(@Path("region") region: String): Result<List<StationInfo>>
}