package com.example.epetrol.service

import com.example.epetrol.data.RegionGasStation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RegionGasStationsService {
    @GET("api/v1/fuel-info")
    suspend fun getGasStations(
        @Query("regionLatin") region: String,
        @Header("Authorization") token: String
    ): List<RegionGasStation>
}